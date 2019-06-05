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
import com.spring.order.service.OrderItemsService;
import com.spring.order.service.OrderManagementService;
import com.spring.order.service.OrderService;
import com.spring.order.vo.OrderDetailsVo;
import com.spring.order.vo.OrderItemsVo;
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
					orderDetailsVos = mapper.readValue(gson.toJson(objList),new TypeReference<List<OrderDetailsVo>>() {
							});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(orderDetailsVos != null && !orderDetailsVos.isEmpty()) {

					logger.info("Order details fetched succssfully"+orderId);
					responseMap.put("status", "error");
					responseMap.put("message", "Order details fetched succssfully:");
					responseMap.put("object", orderDetailsVos);
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
	public Map<String, Object> updateOrder(BigInteger orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> cancelOrder(BigInteger orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> checkOrderStatus(BigInteger orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> listAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
