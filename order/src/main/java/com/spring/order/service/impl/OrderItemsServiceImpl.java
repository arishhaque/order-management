package com.spring.order.service.impl;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.order.dao.ItemDao;
import com.spring.order.dao.OrderDao;
import com.spring.order.dao.OrderItemsDao;
import com.spring.order.model.Item;
import com.spring.order.model.Order;
import com.spring.order.model.OrderItems;
import com.spring.order.service.OrderItemsService;
import com.spring.order.vo.OrderItemsVo;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderItemsDao orderItemsDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Override
	public Map<String, Object> create(OrderItemsVo orderItemsVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(orderItemsVo != null) {
			
			OrderItems orderItems = new OrderItems();
			if(orderItemsVo.getOrderId() != null) {
				Order order = orderDao.read(orderItemsVo.getOrderId());
				if(order!=null)
					orderItems.setOrder(order);
				else {
					logger.info("Order could not be placed, invalid order id");
					responseMap.put("status", "success");
					responseMap.put("message", "Order could not be placed, invalid order id");
					return responseMap;
				}
			}
			else {
				logger.info("Order could not be placed, invalid order id");
				responseMap.put("status", "success");
				responseMap.put("message", "Order could not be placed, invalid order id");
				return responseMap;
			}
			if(orderItemsVo.getItemId() != null) {
				Item item = itemDao.read(orderItemsVo.getItemId());
				if(item!=null)
					orderItems.setItem(item);
				else {
					logger.info("Order could not be placed, invalid item id");
					responseMap.put("status", "success");
					responseMap.put("message", "Order could not be placed, invalid item id");
					return responseMap;
				}
			}
			else {
				logger.info("Order could not be placed, invalid item id");
				responseMap.put("status", "success");
				responseMap.put("message", "Order could not be placed, invalid item id");
				return responseMap;
			}
			if(orderItemsVo.getPrice() != null)
				orderItems.setPrice(orderItemsVo.getPrice());
			else {
				logger.info("Order could not be placed, invalid item id");
				responseMap.put("status", "success");
				responseMap.put("message", "Order could not be placed, invalid item id");
				return responseMap;
			}
			orderItems.setOrderStatus(orderItemsVo.getOrderStatus() != null && !orderItemsVo.getOrderStatus().isEmpty() ?
					orderItemsVo.getOrderStatus():"placed successfully");
			orderItems.setQuantity((orderItemsVo.getQuantity() != null  ? orderItemsVo.getQuantity():new BigInteger("1")));
			orderItems.setActive(orderItemsVo.getActive() != null ? orderItemsVo.getActive():true);
			orderItems.setDate(orderItemsVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderItemsVo.getDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());
			
			BigInteger id = (BigInteger) orderItemsDao.create(orderItems);
			if(id != null) {
				
				logger.info("Order placed successfully");
				responseMap.put("status", "success");
				responseMap.put("message", "Order placed successfully");
				responseMap.put("id", id);
				return responseMap;
				
			}
			
		}
		logger.error("Order cannot be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;

	}

	@Override
	public Map<String, Object> createBatch(List<OrderItemsVo> orderItemsVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<OrderItems> orderItemsList = new ArrayList<>();
		if(orderItemsVos != null && !orderItemsVos.isEmpty()) {
			
			for(OrderItemsVo orderItemsVo : orderItemsVos ) {
				
				if(orderItemsVo != null) {
					
					OrderItems orderItems = new OrderItems();
					if(orderItemsVo.getOrderId() != null) {
						Order order = orderDao.read(orderItemsVo.getOrderId());
						if(order!=null)
							orderItems.setOrder(order);
						else {
							logger.info("Order could not be placed, invalid order id");
							responseMap.put("status", "success");
							responseMap.put("message", "Order could not be placed, invalid order id");
							return responseMap;
						}
					}
					else {
						logger.info("Order could not be placed, invalid order id");
						responseMap.put("status", "success");
						responseMap.put("message", "Order could not be placed, invalid order id");
						return responseMap;
					}
					if(orderItemsVo.getItemId() != null) {
						Item item = itemDao.read(orderItemsVo.getItemId());
						if(item!=null)
							orderItems.setItem(item);
						else {
							logger.info("Order could not be placed, invalid item id");
							responseMap.put("status", "success");
							responseMap.put("message", "Order could not be placed, invalid item id");
							return responseMap;
						}
					}
					else {
						logger.info("Order could not be placed, invalid item id");
						responseMap.put("status", "success");
						responseMap.put("message", "Order could not be placed, invalid item id");
						return responseMap;
					}
					if(orderItemsVo.getPrice() != null)
						orderItems.setPrice(orderItemsVo.getPrice());
					else {
						logger.info("Order could not be placed, invalid item id");
						responseMap.put("status", "success");
						responseMap.put("message", "Order could not be placed, invalid item id");
						return responseMap;
					}
					orderItems.setOrderStatus(orderItemsVo.getOrderStatus() != null && !orderItemsVo.getOrderStatus().isEmpty() ?
							orderItemsVo.getOrderStatus():"placed successfully");
					orderItems.setQuantity((orderItemsVo.getQuantity() != null  ? orderItemsVo.getQuantity():new BigInteger("1")));
					orderItems.setActive(orderItemsVo.getActive() != null ? orderItemsVo.getActive():true);
					orderItems.setDate(orderItemsVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderItemsVo.getDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());
					
					
					orderItemsList.add(orderItems);
					}
					
				}
			List<BigInteger> ids = null;
			if(orderItemsList!=null && !orderItemsList.isEmpty()) {
				ids = orderItemsDao.createBatch(orderItemsList);
				if(ids != null && !ids.isEmpty()) {
					
					logger.info("Order placed successfully");
					responseMap.put("status", "success");
					responseMap.put("message", "Order placed successfully");
					responseMap.put("ids", ids);
					return responseMap;
				}
			}
		}
		logger.error("Order cannot be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;
	}

	@Override
	public Map<String, Object> update(OrderItemsVo orderItemsVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(orderItemsVo != null && orderItemsVo.getId() != null) {
			
			OrderItems orderItems = (OrderItems) orderItemsDao.read(orderItemsVo.getId());
			
			if(orderItems!=null) {
				if(orderItemsVo.getOrderId() != null) {
					Order order = orderDao.read(orderItemsVo.getOrderId());
					if(order!=null)
						orderItems.setOrder(order);
					else {
						logger.info("Order could not be placed, invalid order id");
						responseMap.put("status", "success");
						responseMap.put("message", "Order could not be placed, invalid order id");
						return responseMap;
					}
				}
				else {
					logger.info("Order could not be placed, invalid order id");
					responseMap.put("status", "success");
					responseMap.put("message", "Order could not be placed, invalid order id");
					return responseMap;
				}
				
				if(orderItemsVo.getItemId() != null) {
					Item item = itemDao.read(orderItemsVo.getItemId());
					if(item!=null)
						orderItems.setItem(item);
					else {
						logger.info("Order could not be placed, invalid item id");
						responseMap.put("status", "success");
						responseMap.put("message", "Order could not be placed, invalid item id");
						return responseMap;
					}
				}
				else {
					logger.info("Order could not be placed, invalid item id");
					responseMap.put("status", "success");
					responseMap.put("message", "Order could not be placed, invalid item id");
					return responseMap;
				}
				
				orderItems.setPrice(orderItemsVo.getPrice() != null ?orderItemsVo.getPrice():orderItems.getPrice());
					orderItems.setPrice(orderItemsVo.getPrice());
				orderItems.setOrderStatus(orderItemsVo.getOrderStatus() != null && !orderItemsVo.getOrderStatus().isEmpty() ?
						orderItemsVo.getOrderStatus():orderItems.getOrderStatus());
				orderItems.setQuantity((orderItemsVo.getQuantity() != null  ? orderItemsVo.getQuantity():orderItems.getQuantity()));
				orderItems.setActive(orderItemsVo.getActive() != null ? orderItemsVo.getActive():orderItems.getActive());
				orderItems.setDate(orderItemsVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderItemsVo.getDate()),TimeZone.getDefault().toZoneId()) : orderItems.getDate());
				
				if(orderItems != null) {
					orderItemsDao.update(orderItems);	
					logger.info("Order updated successfully");
					responseMap.put("status", "success");
					responseMap.put("message", "Order updated successfully");
					return responseMap;
				}
			}
		}
			
		logger.error("Order cannot be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;

	
	}

	@Override
	public Map<String, Object> updateBatch(List<OrderItemsVo> orderItemsVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<OrderItems> orderItemsList = new ArrayList<>();
		if(orderItemsVos!=null && !orderItemsVos.isEmpty()) {
			for(OrderItemsVo orderItemsVo:orderItemsVos) {
				if(orderItemsVo != null && orderItemsVo.getId() != null) {
					
					OrderItems orderItems = (OrderItems) orderItemsDao.read(orderItemsVo.getId());
					if(orderItems!=null) {
						if(orderItemsVo.getOrderId() != null) {
							Order order = orderDao.read(orderItemsVo.getOrderId());
							if(order!=null)
								orderItems.setOrder(order);
							else {
								logger.info("Order could not be placed, invalid order id");
								responseMap.put("status", "success");
								responseMap.put("message", "Order could not be placed, invalid order id");
								return responseMap;
							}
						}
						else {
							logger.info("Order could not be placed, invalid order id");
							responseMap.put("status", "success");
							responseMap.put("message", "Order could not be placed, invalid order id");
							return responseMap;
						}
						
						if(orderItemsVo.getItemId() != null) {
							Item item = itemDao.read(orderItemsVo.getItemId());
							if(item!=null)
								orderItems.setItem(item);
							else {
								logger.info("Order could not be placed, invalid item id");
								responseMap.put("status", "success");
								responseMap.put("message", "Order could not be placed, invalid item id");
								return responseMap;
							}
						}
						else {
							logger.info("Order could not be placed, invalid item id");
							responseMap.put("status", "success");
							responseMap.put("message", "Order could not be placed, invalid item id");
							return responseMap;
						}
						orderItems.setPrice(orderItemsVo.getPrice() != null ?orderItemsVo.getPrice():orderItems.getPrice());
							orderItems.setPrice(orderItemsVo.getPrice());
						orderItems.setOrderStatus(orderItemsVo.getOrderStatus() != null && !orderItemsVo.getOrderStatus().isEmpty() ?
								orderItemsVo.getOrderStatus():orderItems.getOrderStatus());
						orderItems.setQuantity((orderItemsVo.getQuantity() != null  ? orderItemsVo.getQuantity():orderItems.getQuantity()));
						orderItems.setActive(orderItemsVo.getActive() != null ? orderItemsVo.getActive():orderItems.getActive());
						orderItems.setDate(orderItemsVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderItemsVo.getDate()),TimeZone.getDefault().toZoneId()) : orderItems.getDate());
						
						orderItemsList.add(orderItems);
							
					}
				}
				
			}
			if(orderItemsList != null && !orderItemsList.isEmpty()) {
				orderItemsDao.updateBatch(orderItemsList);	
				logger.info("Order updated successfully");
				responseMap.put("status", "success");
				responseMap.put("message", "Order updated successfully");
				return responseMap;
			}
		}
			
			
		logger.error("Order cannot be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;

	
	
	}

	@Override
	public Map<String, Object> read(BigInteger id) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(id != null) {
			
			OrderItems orderItems = (OrderItems) orderItemsDao.read(id);
			if(orderItems != null && orderItems.getId()!=null) {
				
				OrderItemsVo orderItemsVo = new OrderItemsVo();
				orderItemsVo.setId(orderItems.getId());
				orderItemsVo.setOrderId(orderItems.getOrder().getId());
				orderItemsVo.setItemId(orderItems.getItem().getId());
				orderItemsVo.setActive(orderItems.getActive());
				orderItemsVo.setQuantity(orderItems.getQuantity());
				orderItemsVo.setPrice(orderItems.getPrice());
				orderItemsVo.setOrderStatus(orderItems.getOrderStatus());
				orderItemsVo.setDate(orderItems.getDate()!=null ? 
						orderItems.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli():null);
				
				logger.info("Orders and items found with id"+id);
				responseMap.put("status","success");
				responseMap.put("message","Order and items found with id"+id);
				responseMap.put("object", orderItemsVo);
				return responseMap;
			}
		}
		
		logger.error("Order cannot be found:"+id);
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot find order and items"+id);
		return responseMap;
	
	}

	@Override
	public Map<String, Object> delete(BigInteger id) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(id != null) {
			
			OrderItems orderItems = (OrderItems) orderItemsDao.read(id);
			if(orderItems != null) {
				orderItems.setActive(false);
				orderItemsDao.update(orderItems);	
				logger.info("Order deleted successfully");
				responseMap.put("status", "success");
				responseMap.put("message", "Order updated successfully");
				return responseMap;
			}
				
		}
			
		logger.error("Order cannot be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;

	
	
	}

	@Override
	public Map<String, Object> list() {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<OrderItemsVo> orderItemsVos = new ArrayList<>();
		List<OrderItems> orderItemsList = (List<OrderItems>) orderItemsDao.findAllOrderItems();
		if(orderItemsList != null && !orderItemsList.isEmpty()) {
		
			for(OrderItems orderItems: orderItemsList)
				if(orderItems != null && orderItems.getId()!=null) {
						
						OrderItemsVo orderItemsVo = new OrderItemsVo();
						orderItemsVo.setId(orderItems.getId());
						orderItemsVo.setOrderId(orderItems.getOrder().getId());
						orderItemsVo.setItemId(orderItems.getItem().getId());
						orderItemsVo.setActive(orderItems.getActive());
						orderItemsVo.setQuantity(orderItems.getQuantity());
						orderItemsVo.setPrice(orderItems.getPrice());
						orderItemsVo.setOrderStatus(orderItems.getOrderStatus());
						orderItemsVo.setDate(orderItems.getDate()!=null ? 
								orderItems.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli():null);
						
						orderItemsVos.add(orderItemsVo);
				}
			
			logger.info("Orders and items Records found");
			responseMap.put("status","success");
			responseMap.put("message","Order and items Records found");
			responseMap.put("object", orderItemsVos);
			return responseMap;
		}
		
		logger.error("Cannot find order and items Records");
		responseMap.put("status", "error");
		responseMap.put("message", "ECannot find order and items Records");
		return responseMap;
	
	
	}
	
	
	
}

