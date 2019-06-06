package com.spring.order.service.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
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
import com.spring.order.vo.ItemStatusVo;
import com.spring.order.vo.OrderDetailsVo;
import com.spring.order.vo.OrderItemStatusVo;
import com.spring.order.vo.OrderItemsVo;
import com.spring.order.vo.OrderStatusVo;
import com.spring.order.vo.PlaceOrderItemVo;
import com.spring.order.vo.PlaceOrderVo;
import com.spring.order.vo.SearchItemVo;
import com.spring.order.vo.SearchOrderVo;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
		List<OrderItemsVo> orderItemsVos = new ArrayList<>();
		if(placeOrderVo != null) {
			
			Order order = new Order();
			if(placeOrderVo.getEmailId() == null || placeOrderVo.getEmailId().trim().isEmpty()) {
				logger.error("Invalid email id");
				responseMap.put("status", "error");
				responseMap.put("message", "Invalid email id");
				return responseMap;
			}
			order.setEmailId(placeOrderVo.getEmailId().trim());
			order.setDescription((placeOrderVo.getDescription() != null ? placeOrderVo.getDescription():null));
			order.setActive(true);
			order.setDate(LocalDateTime.now());
			
			if(placeOrderVo.getPlaceOrderItemVos() == null && placeOrderVo.getPlaceOrderItemVos().isEmpty()) {
				
				logger.error("No items found");
				responseMap.put("status", "error");
				responseMap.put("message", "No items found, check items list");
				return responseMap;
			}
			
			List<PlaceOrderItemVo> placeOrderItemVos = placeOrderVo.getPlaceOrderItemVos();
			
			BigInteger quantity = new BigInteger("1");
			BigInteger itemId = null;
			List<Item> items = new ArrayList<>();
			Map<BigInteger, BigInteger> itemQuantityMap = new HashMap<>();
			for(PlaceOrderItemVo placeOrderItemVo :placeOrderItemVos) {
				
				quantity = (placeOrderItemVo.getQuantity() != null ? placeOrderItemVo.getQuantity():new BigInteger("1"));
				itemId = (placeOrderItemVo.getItemId() != null ? placeOrderItemVo.getItemId():new BigInteger("0"));
				itemQuantityMap.put(itemId, quantity);
				Item item =  itemDao.read(itemId);
				if(item!=null && item.getActive() && item.getItemCount().compareTo(quantity) > -1) {
					item.setItemCount(item.getItemCount().subtract(quantity));
					items.add(item);
				} else {
					logger.error("item is out of stock with id: "+itemId);
					responseMap.put("status", "error");
					responseMap.put("message", "item is out of stock with id: "+itemId);
					return responseMap;
				}
			}
			
			if(placeOrderVo.getPrice() == null) {
				logger.error("Price not specified");
				responseMap.put("status", "error");
				responseMap.put("message", "Price not specified");
				return responseMap;
			}
			BigInteger price = placeOrderVo.getPrice();
			BigInteger orderId = orderDao.create(order);
			if(orderId == null) {
				logger.error("Order could not be placed");
				responseMap.put("status", "error");
				responseMap.put("message", "Order could not be placed");
				return responseMap;
			}
			
			if(items != null && !items.isEmpty()) {

				itemDao.updateBatch(items);
				for(Item item : items) {
					
					OrderItemsVo orderItemsVo = new OrderItemsVo();
					orderItemsVo.setOrderId(orderId);
					orderItemsVo.setItemId(item.getId());
					orderItemsVo.setQuantity(itemQuantityMap.get(item.getId()));
					orderItemsVo.setPrice(price);
					
					orderItemsVos.add(orderItemsVo);
				};
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
		responseMap.put("message", "Order could not be placed, pls review your order");
		return responseMap;

	}
	
	@Override
	public Map<String, Object> placeBulkOrders(List<PlaceOrderVo> placeOrderVos) {
		
		Map<String, Object> responseMap = new HashMap<>();
	
		List<OrderItemsVo> orderItemsVos = new ArrayList<>();
		List<BigInteger> orderIds = new ArrayList<>();
		for(PlaceOrderVo placeOrderVo : placeOrderVos) {
			
			if(placeOrderVo != null) {
				
				Order order = new Order();
				if(placeOrderVo.getEmailId() == null || placeOrderVo.getEmailId().trim().isEmpty()) {
					logger.error("Invalid email id");
					responseMap.put("status", "error");
					responseMap.put("message", "Invalid email id");
					return responseMap;
				}
				
				order.setEmailId(placeOrderVo.getEmailId().trim());
			
				order.setDescription((placeOrderVo.getDescription() != null ? placeOrderVo.getDescription():null));
				order.setActive(true);
				order.setDate(LocalDateTime.now());
				
				if(placeOrderVo.getPlaceOrderItemVos() == null && placeOrderVo.getPlaceOrderItemVos().isEmpty()) {
					
					logger.error("No items found");
					responseMap.put("status", "error");
					responseMap.put("message", "No items found, check items list");
					return responseMap;
				}
				
				List<PlaceOrderItemVo> placeOrderItemVos = placeOrderVo.getPlaceOrderItemVos();
				
				BigInteger quantity = new BigInteger("1");
				BigInteger itemId = null;
				List<Item> items = new ArrayList<>();
				Map<BigInteger, BigInteger> itemQuantityMap = new HashMap<>();
				for(PlaceOrderItemVo placeOrderItemVo :placeOrderItemVos) {
					
					quantity = (placeOrderItemVo.getQuantity() != null ? placeOrderItemVo.getQuantity():new BigInteger("1"));
					itemId = (placeOrderItemVo.getItemId() != null ? placeOrderItemVo.getItemId():new BigInteger("0"));
					itemQuantityMap.put(itemId, quantity);
					Item item =  itemDao.read(itemId);
					if(item!=null && item.getActive() && item.getItemCount().compareTo(quantity) > -1) {
						item.setItemCount(item.getItemCount().subtract(quantity));
						items.add(item);
					} else {
						logger.error("item is out of stock with id: "+itemId);
						responseMap.put("status", "error");
						responseMap.put("message", "item is out of stock with id: "+itemId);
						return responseMap;
					}
				}
				
				if(placeOrderVo.getPrice() == null) {
					logger.error("Price not specified");
					responseMap.put("status", "error");
					responseMap.put("message", "Price not specified");
					return responseMap;
				}
				BigInteger price = placeOrderVo.getPrice();
				
				BigInteger orderId = orderDao.create(order);
				if(orderId == null) {
					logger.error("Order could not be placed");
					responseMap.put("status", "error");
					responseMap.put("message", "Order could not be placed");
					return responseMap;
				}
				
				orderIds.add(orderId);
			
				if(items != null && !items.isEmpty()) {
					
					itemDao.updateBatch(items);
					items.forEach((item) -> {
						
						OrderItemsVo orderItemsVo = new OrderItemsVo();
						orderItemsVo.setOrderId(orderId);
						orderItemsVo.setItemId(item.getId());
						orderItemsVo.setPrice(price);
						orderItemsVo.setQuantity(itemQuantityMap.get(item.getId()));
						
						orderItemsVos.add(orderItemsVo);
					});
				}
			}
			
		}
		if(orderItemsVos != null && !orderItemsVos.isEmpty()) {
			
			responseMap = orderItemsService.createBatch(orderItemsVos);
			if(responseMap != null && !responseMap.isEmpty() && responseMap.get("status").equals("success")) {
				
				logger.info("Order placed successfully");
				responseMap.put("status", "success");
				responseMap.put("message", "Order placed successfully");
				responseMap.put("ids", orderIds);
				return responseMap;
			}
		}

		
		logger.error("Order could not be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "Order could not be placed, pls review your order");
		return responseMap;
	
	
	}

	@Override
	public Map<String, Object> getOrderDetailsById(BigInteger orderId) {
		
		Map<String, Object> responseMap = new HashMap<>();
		List<OrderDetailsVo> orderDetailsVos = new ArrayList<>();
		if(orderId != null) {
			
			Order order = orderDao.read(orderId);
			if(order != null && order.getActive()) {
				
				ObjectMapper mapper = new ObjectMapper();
				GsonBuilder builder = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Gson gson = builder.create();
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("orderId", orderId);
				
				List<Object[]> queryResult = orderItemsDao.getOrderDetailsById(parameters);
				try {
					List<Object> objList = orderItemsDao.convertToObjectList(queryResult,new OrderDetailsVo(), null, null);
					orderDetailsVos = mapper.readValue(gson.toJson(objList),new TypeReference<List<OrderDetailsVo>>() {});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(orderDetailsVos != null && !orderDetailsVos.isEmpty()) {

					logger.info("Order details fetched succssfully: "+orderId);
					responseMap.put("status", "success");
					responseMap.put("message", "Order details fetched succssfully: "+orderId);
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
	public Map<String,Object> listOrderDetailsWithSearchKey(String searchKey, Integer pageSize, Integer pageNumber){
		
		Map<String, Object> responseMap = new HashMap<>();
		List<OrderDetailsVo> orderDetailsVos = new ArrayList<>();
		List<SearchOrderVo> searchOrderVos = new ArrayList<>();
		List<SearchOrderVo> searchOrderDetailsPaginatedVos = new ArrayList<>();
		
		if(searchKey == null || pageSize == null || pageNumber == null) {
			
			logger.error("No orders matched");
			responseMap.put("status", "error");
			responseMap.put("message", "Enter valid search key, page number or page size");
			return responseMap;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		GsonBuilder builder = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		Gson gson = builder.create();
		
		List<Object[]> queryResult = null;
		if(!searchKey.trim().isEmpty()) {
			
			searchKey = "%" + searchKey.trim() + "%";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("searchKey", searchKey.trim());
			
			queryResult = orderItemsDao.getOrderDetailsBySearchKey(parameters);
		}else {
		
			queryResult = orderItemsDao.getOrderDetailsByEmptySearchKey();
		}
		
		try {
			List<Object> objList = orderItemsDao.convertToObjectList(queryResult,new OrderDetailsVo(), null, null);
			orderDetailsVos = mapper.readValue(gson.toJson(objList),new TypeReference<List<OrderDetailsVo>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<BigInteger, SearchOrderVo> ordersMap = new HashMap<>();
		
		if (orderDetailsVos != null && !orderDetailsVos.isEmpty()) {

			orderDetailsVos.forEach((vo -> {
				
				if(ordersMap.containsKey(vo.getOrderId())) {
					
					SearchOrderVo searchOrderVo = ordersMap.get(vo.getOrderId());
					SearchItemVo searchItemVo = new SearchItemVo();
					searchItemVo.setItemId(vo.getItemId());
					searchItemVo.setQuantity(vo.getQuantity());
					searchItemVo.setOrderStatus(vo.getOrderStatus());
					searchOrderVo.getItemDetails().add(searchItemVo);
					
					ordersMap.put(vo.getOrderId(), searchOrderVo);
										
				}else{
					
					SearchOrderVo searchOrderVo = new SearchOrderVo();
					searchOrderVo.setOrderId(vo.getOrderId());
					searchOrderVo.setEmailId(vo.getEmailId());
					searchOrderVo.setDescription(vo.getDescription());
					searchOrderVo.setPrice(vo.getPrice());
					
					SearchItemVo searchItemVo = new SearchItemVo();
					searchItemVo.setItemId(vo.getItemId());
					searchItemVo.setQuantity(vo.getQuantity());
					searchItemVo.setOrderStatus(vo.getOrderStatus());
					
					searchOrderVo.setItemDetails(new ArrayList<>());
					searchOrderVo.getItemDetails().add(searchItemVo);
					
					ordersMap.put(vo.getOrderId(), searchOrderVo);
				}
				
					
			}));
		
			if(ordersMap != null && !ordersMap.isEmpty()) {
				
				ordersMap.keySet().forEach(vo -> {
					searchOrderVos.add(ordersMap.get(vo));
				});
				
			}
			
			if(searchOrderVos != null && !searchOrderVos.isEmpty()) {
			
				Integer totalCount = searchOrderVos.size();
				Integer indexstart = (pageNumber - 1) * pageSize;
				Integer pages = totalCount / pageSize;
	
				if (pages + 1 >= pageNumber) {
					if (pages + 1 == pageNumber)
						pageSize = totalCount % pageSize;
	
					for (int i = indexstart; i < indexstart + pageSize; i++) {
	
						SearchOrderVo searchOrderVo = searchOrderVos.get(i);
						searchOrderDetailsPaginatedVos.add(searchOrderVo);
					}
				}
			}
		}
		
		if(searchOrderDetailsPaginatedVos != null && !searchOrderDetailsPaginatedVos.isEmpty()) {
			
			logger.info("Order details fetched succssfully");
			responseMap.put("status", "success");
			responseMap.put("message", "Order details fetched succssfully");
			responseMap.put("totalOrdersCount", ordersMap.keySet().size());
			responseMap.put("searchOrderDetails", searchOrderDetailsPaginatedVos);
			return responseMap;
		}
		
		
		logger.error("No orders matched");
		responseMap.put("status", "error");
		responseMap.put("message", "No orders matched");
		return responseMap;
	}


}
