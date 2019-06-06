package com.spring.order.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spring.order.dao.ItemDao;
import com.spring.order.dao.OrderDao;
import com.spring.order.dao.OrderItemsDao;
import com.spring.order.model.Item;
import com.spring.order.model.Order;
import com.spring.order.model.OrderItems;
import com.spring.order.service.OrderItemsService;
import com.spring.order.service.OrderManagementService;
import com.spring.order.service.OrderService;
import com.spring.order.vo.ItemStatusVo;
import com.spring.order.vo.OrderDetailsVo;
import com.spring.order.vo.OrderItemStatusVo;
import com.spring.order.vo.OrderItemsVo;
import com.spring.order.vo.OrderStatusVo;
import com.spring.order.vo.OrderVo;
import com.spring.order.vo.PlaceOrderVo;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemsService orderItemsService;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private OrderItemsDao orderItemsDao;
	
	@Override
	public Map<String, Object> placeOrder(PlaceOrderVo placeOrderVo) {
		
		Map<String, Object> responseMap = new HashMap<>();
		if(placeOrderVo != null) {
			
			OrderVo orderVo = new OrderVo();
			if(placeOrderVo.getEmailId() == null || placeOrderVo.getEmailId().trim().isEmpty()) {
				logger.error("Invalid email id");
				responseMap.put("status", "error");
				responseMap.put("message", "Invalid email id");
				return responseMap;
			}
			
			orderVo.setEmailId(placeOrderVo.getEmailId().trim());
			if(placeOrderVo.getItemIds() == null || placeOrderVo.getItemIds().isEmpty()) {
				logger.error("No items found");
				responseMap.put("status", "error");
				responseMap.put("message", "No items found, check items list");
				return responseMap;
			}
			
			String Description = (placeOrderVo.getDescription() != null ? placeOrderVo.getDescription():null);
			orderVo.setDescription(Description);
			BigInteger quantity = (placeOrderVo.getQuantity() != null ? placeOrderVo.getQuantity():new BigInteger("1"));
			if(placeOrderVo.getPrice() == null) {
				logger.error("Price not specified");
				responseMap.put("status", "error");
				responseMap.put("message", "Price not specified");
				return responseMap;
			}
			BigInteger price = placeOrderVo.getPrice();

			Map<String, Object> orderResponseMap = orderService.create(orderVo);
			if(orderResponseMap == null || orderResponseMap.isEmpty() || 
					(orderResponseMap.containsKey("status") && orderResponseMap.get("status").equals("error"))){
				
				logger.error("Order could not be placed");
				responseMap.put("status", "error");
				responseMap.put("message", "Order could not be placed");
				return responseMap;
			}
			
			List<OrderItemsVo> orderItemsVos = new ArrayList<>();
			BigInteger orderId = (BigInteger) orderResponseMap.get("id");
			if(orderId == null) {
				logger.error("Order could not be placed");
				responseMap.put("status", "error");
				responseMap.put("message", "Order could not be placed");
				return responseMap;
			}
			
			List<BigInteger> itemIds = placeOrderVo.getItemIds();
			List<Item> items = null;
			if(itemIds != null && !itemIds.isEmpty()) {
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("idList", itemIds);
				items = (List<Item>) itemDao.getItemsByIds(parameters);
				if(items!=null && !items.isEmpty())
					itemDao.updateBatch(items);
				else {
					logger.error("items not specified");
					responseMap.put("status", "error");
					responseMap.put("message", "items not specified, select appropriate items");
					return responseMap;
				}
			}
			
			if(items != null && !items.isEmpty()) {
				
				items.forEach((item) -> {
					
					OrderItemsVo orderItemsVo = new OrderItemsVo();
					orderItemsVo.setOrderId(orderId);
					orderItemsVo.setItemId(item.getId());
					orderItemsVo.setPrice(price);
					orderItemsVo.setQuantity(quantity);
					
					orderItemsVos.add(orderItemsVo);
				});
			}
			
			if(orderItemsVos != null && !orderItemsVos.isEmpty()) {
				
				responseMap = orderItemsService.createBatch(orderItemsVos);
				if(responseMap != null && !responseMap.isEmpty() && responseMap.get("status").equals("success")) {
					
					if(responseMap.containsKey("ids"))
						responseMap.remove("ids");
					logger.info("Order placed successfully");
					responseMap.put("status", "success");
					responseMap.put("message", "Order placed successfully");
					responseMap.put("id", orderId);
					return responseMap;
				}
			}
		}
		
		logger.error("Order could not be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "Order could not be placed, try again!");
		return responseMap;

	}

	@Override
	public Map<String, Object> getOrderDetailsById(BigInteger orderId) {
		
		Map<String, Object> responseMap = new HashMap<>();
		List<OrderDetailsVo> orderDetailsVos = new ArrayList<>();
		if(orderId != null) {
			
			Order order = orderDao.read(orderId);
			if(order != null && order.getActive()) {
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("orderId", orderId);
				List<Object[]> queryResult = orderItemsDao.getOrderDetailsById(parameters);
				
				ObjectMapper mapper = new ObjectMapper();
				GsonBuilder builder = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Gson gson = builder.create();
				
				try {
					List<Object> objList = orderItemsDao.convertToObjectList(queryResult,new OrderDetailsVo(), null, null);
					orderDetailsVos = mapper.readValue(gson.toJson(objList),new TypeReference<List<OrderDetailsVo>>() {});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(orderDetailsVos != null && !orderDetailsVos.isEmpty()) {

					logger.info("Order details fetched succssfully"+orderId);
					responseMap.put("status", "success");
					responseMap.put("message", "Order details fetched succssfully:");
					responseMap.put("OrderDetails", orderDetailsVos);
					return responseMap;
				}
			}
		}
		
		logger.error("Order does not exist, invalid order id"+orderId);
		responseMap.put("status", "error");
		responseMap.put("message", "Could not fetch order details, invalid order id :"+orderId);
		return responseMap;
	}

	@Override
	public Map<String, Object> updateOrderStatus(BigInteger orderId,  List<ItemStatusVo> orderItemStatusVos) {
		
		Map<String, Object> responseMap = new HashMap<>();
		List<OrderItems> orderItemsList = new ArrayList<>();
		if(orderId != null && orderItemStatusVos != null && !orderItemStatusVos.isEmpty()) {
			
			for(ItemStatusVo orderItemStatusVo : orderItemStatusVos)
				
				if(orderItemStatusVo != null && orderItemStatusVo.getItemId() != null 
						&& orderItemStatusVo.getOrderStatus() != null && !orderItemStatusVo.getOrderStatus().trim().isEmpty()) {
					
					Map<String, Object> parameters = new HashMap<>();
					parameters.put("orderId", orderId);
					parameters.put("itemId", orderItemStatusVo.getItemId());
					
					OrderItems orderItems = orderItemsDao.getOrderItemsByOrderIdandItemId(parameters);
					if(orderItems != null ) {
						orderItems.setOrderStatus(orderItemStatusVo.getOrderStatus());
						orderItemsList.add(orderItems);
					}else {
						logger.error("Order does not exist, invalid order or items selected"+orderId);
						responseMap.put("status", "error");
						responseMap.put("message", "Could not fetch order details, invalid order id: "+orderId+ " or item id: "+orderItemStatusVo.getItemId());
						return responseMap;
	
					}
				}
			
			if(orderItemsList != null && !orderItemsList.isEmpty()) {
				orderItemsDao.updateBatch(orderItemsList);
				logger.info("Order status updated successfully"+orderId);
				responseMap.put("status", "success");
				responseMap.put("message", "Order status updated successfully");
				responseMap.put("orderId", orderId);
				return responseMap;
			}
		}
		
		logger.error("Order does not exist, invalid order or items selected"+orderId);
		responseMap.put("status", "error");
		responseMap.put("message", "Could not fetch order details, invalid order id :"+orderId);
		return responseMap;
		
	}

	@Override
	public Map<String, Object> checkOrderStatus(OrderItemStatusVo orderItemStatusVo) {
		
		Map<String, Object> responseMap = new HashMap<>();
		BigInteger orderId = null;
		List<BigInteger> itemIds = new ArrayList<>();
		OrderStatusVo orderStatusVo = new OrderStatusVo();
		List<ItemStatusVo> itemStatusVos = new ArrayList<>();
		if(orderItemStatusVo != null && orderItemStatusVo.getOrderId() != null
				&& orderItemStatusVo.getItemIds() != null && !orderItemStatusVo.getItemIds().isEmpty()) {
			
			orderId = orderItemStatusVo.getOrderId();
			itemIds = orderItemStatusVo.getItemIds();
			
			orderStatusVo.setOrderId(orderId);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("orderId", orderId);
			parameters.put("itemIds", itemIds);
			
			List<OrderItems> orderItems = orderItemsDao.listOrderItemsByOrderIdandItemIds(parameters);
			if(orderItems != null && !orderItems.isEmpty()) {
				
				orderItems.forEach((orderItem) -> {
					
					ItemStatusVo itemStatusVo = new ItemStatusVo();
					itemStatusVo.setItemId(orderItem.getItem().getId());
					itemStatusVo.setOrderStatus(orderItem.getOrderStatus());
					itemStatusVos.add(itemStatusVo);
				});
			}
		}
		
		if(itemStatusVos != null && !itemStatusVos.isEmpty()) {
			
			orderStatusVo.setItemStatusVos(itemStatusVos);
			orderStatusVo.setOrderId(orderId);
			
			logger.info("Order status fetched successfully"+orderId);
			responseMap.put("status", "success");
			responseMap.put("message", "Order status fetched successfully");
			responseMap.put("orderStatus", orderStatusVo);
			return responseMap;
			
		}
		
		logger.error("Order does not exist, invalid order or items selected"+orderId);
		responseMap.put("status", "error");
		responseMap.put("message", "Could not fetch order details, invalid order id :"+orderId);
		return responseMap;
	}

	@Override
	public Map<String, Object> listAllOrderDetails() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
