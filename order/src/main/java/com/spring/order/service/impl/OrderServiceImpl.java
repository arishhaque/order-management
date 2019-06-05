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

import com.spring.order.dao.OrderDao;
import com.spring.order.model.Order;
import com.spring.order.service.OrderService;
import com.spring.order.vo.OrderVo;

@Service
public class OrderServiceImpl implements OrderService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderDao orderDao;
	
	@Override
	public Map<String, Object> create(OrderVo orderVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(orderVo != null) {
			
			Order order = new Order();
			if(orderVo.getEmailId() != null && !orderVo.getEmailId().isEmpty())
				order.setEmailId(orderVo.getEmailId());
			else {
				logger.info("Order could not be placed, invalid email id");
				responseMap.put("status", "success");
				responseMap.put("message", "Order could not be placed, invalid email id");
				return responseMap;
			}
			order.setDescription(orderVo.getDescription());
			order.setActive(orderVo.getActive() != null ? orderVo.getActive():true);
			order.setDate(orderVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderVo.getDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());
			
			BigInteger id = (BigInteger) orderDao.create(order);
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
	public Map<String, Object> createBatch(List<OrderVo> orderVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<Order> orders = new ArrayList<>();
		if(orderVos != null && !orderVos.isEmpty()) {
			
			for(OrderVo orderVo : orderVos) {
				
				Order order = new Order();
				if(orderVo.getEmailId() != null && !orderVo.getEmailId().isEmpty())
					order.setEmailId(orderVo.getEmailId());
				else {
					logger.info("Order could not be placed, invalid email id");
					responseMap.put("status", "success");
					responseMap.put("message", "Order could not be placed, invalid email id");
					return responseMap;
				}
				order.setDescription(orderVo.getDescription());
				order.setActive(orderVo.getActive() != null ? orderVo.getActive():true);
				order.setDate(orderVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderVo.getDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());
				
				orders.add(order);
			}
			
			List<BigInteger> ids = orderDao.createBatch(orders);
			if(ids != null && !ids.isEmpty()) {
				
				logger.info("Order placed with ids:"+ids);
				responseMap.put("status", "success");
				responseMap.put("message", "Orders placed successfully");
				responseMap.put("ids", ids);
				return responseMap;
			}
			
		}
		
		logger.error("Order cannot be placed");
		responseMap.put("status", "error");
		responseMap.put("message", "cannot place Order");
		return responseMap;
	}

	@Override
	public Map<String, Object> update(OrderVo orderVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(orderVo != null && orderVo.getId() != null) {
			
			BigInteger id = orderVo.getId();
			Order order = (Order) orderDao.read(id);
			if(order!=null) {
				
				order.setEmailId(orderVo.getEmailId() != null && !orderVo.getEmailId().isEmpty() ? orderVo.getEmailId():order.getEmailId());
				order.setDescription(orderVo.getDescription() != null && !orderVo.getDescription().isEmpty() ? orderVo.getDescription():order.getDescription());
				order.setActive(orderVo.getActive() != null ? orderVo.getActive():order.getActive());
				order.setDate(orderVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderVo.getDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());
			
				logger.info("Order updated with id:"+id);
				orderDao.update(order);
				logger.info("Order updated with id:"+id);
				responseMap.put("status","success");
				responseMap.put("message","Order updated successfully"+id);
				responseMap.put("id",id);
				return responseMap;
			}
		}
		
		logger.error("Order cannot be updated");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;
	}

	@Override
	public Map<String, Object> updateBatch(List<OrderVo> orderVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<Order> orders = new ArrayList<>(); 
		if(orderVos != null && !orderVos.isEmpty()) {
			for(OrderVo orderVo : orderVos) {
				if(orderVo != null && orderVo.getId() != null) {
					BigInteger id = orderVo.getId();
					Order order = (Order) orderDao.read(id);
					if(order!=null) {
						
						order.setEmailId(orderVo.getEmailId() != null && !orderVo.getEmailId().isEmpty() ? orderVo.getEmailId():order.getEmailId());
						order.setDescription(orderVo.getDescription() != null && !orderVo.getDescription().isEmpty() ? orderVo.getDescription():order.getDescription());
						order.setActive(orderVo.getActive() != null ? orderVo.getActive():order.getActive());
						order.setDate(orderVo.getDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(orderVo.getDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());	
						
						orders.add(order);
					}else {
					
						logger.error("Order cannot be updated");
						responseMap.put("status", "error");
						responseMap.put("message", "Error: Order id not found");
						return responseMap;
					}
				}
			}
			
			logger.info("Order updated successfully");
			orderDao.updateBatch(orders);
			logger.info("Orders updated");
			responseMap.put("status","success");
			responseMap.put("message","Order updated successfully");
			return responseMap;
		}
		
		logger.error("Order cannot be updated");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;
	}

	@Override
	public Map<String, Object> read(BigInteger id) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(id != null) {
			
			Order order = (Order) orderDao.read(id);
			if(order != null) {
				
				OrderVo orderVo = new OrderVo();
				orderVo.setId(order.getId());
				orderVo.setEmailId(order.getEmailId());
				orderVo.setActive(order.getActive());
				orderVo.setDescription(order.getDescription());
				orderVo.setDate(order.getDate()!=null ? order.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli():null);
				
				logger.info("Orders found with id"+id);
				responseMap.put("status","success");
				responseMap.put("message","Order found with id"+id);
				responseMap.put("object", orderVo);
				return responseMap;
			}
		}
		
		logger.error("Order cannot be found:"+id);
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot find order"+id);
		return responseMap;
	}

	@Override
	public Map<String, Object> list() {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<OrderVo> orderVos = new ArrayList<>();
		
		List<Order> orders = orderDao.findAllOrders();
		if(orders != null && !orders.isEmpty()) {
				
			for(Order order : orders) {
					
				OrderVo orderVo = new OrderVo();
				orderVo.setId(order.getId());
				orderVo.setEmailId(order.getEmailId());
				orderVo.setActive(order.getActive());
				orderVo.setDescription(order.getDescription());
				orderVo.setDate(order.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			
				orderVos.add(orderVo);
			}
				
			logger.info("Orders found with id");
			responseMap.put("status","success");
			responseMap.put("message","Order found with id");
			responseMap.put("list", orderVos);
			return responseMap;
		}
		
		
		logger.error("Order cannot be found:");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot find order");
		return responseMap;
	}

	@Override
	public Map<String, Object> delete(BigInteger id) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(id != null) {
			
			Order order = (Order) orderDao.read(id);
			if(order!=null){
				
				order.setActive(false);
				
				logger.info("Order updated with id:"+id);
				orderDao.update(order);
				logger.info("Order updated with id:"+id);
				responseMap.put("status","success");
				responseMap.put("message","Order updated successfully"+id);
				responseMap.put("id",id);
				return responseMap;
			}
		}
		
		logger.error("Order cannot be updated");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot place Order");
		return responseMap;
	}

	
}
