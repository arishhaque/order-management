package com.spring.order.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.order.service.OrderManagementService;
import com.spring.order.vo.PlaceOrderVo;

@RestController
@RequestMapping(value="/rest/api/order-management")
public class OrderManagementController {
	
	@Autowired
	private OrderManagementService orderManagementService;
	
	@RequestMapping(value="/place-order", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> placeOrder(@RequestBody PlaceOrderVo placeOrderVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(placeOrderVo != null) {
			responseMap = orderManagementService.placeOrder(placeOrderVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		return responseMap;
	}
	
	@RequestMapping(value="/get-order-details", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getOrderDetails(@RequestBody Map<String,Object> requestMap) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(requestMap != null && !requestMap.isEmpty() && requestMap.containsKey("orderId")) {
			Integer orderIdInt = (Integer) requestMap.get("orderId");
			BigInteger orderId = BigInteger.valueOf(orderIdInt.intValue());
			responseMap = orderManagementService.getOrderDetailsById(orderId);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		responseMap.put("message","Order is not valid");
		return responseMap;
	}
	
	@RequestMapping(value="/update-order", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateOrder(@RequestBody BigInteger orderId) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderId!=null) {
			responseMap = orderManagementService.updateOrder(orderId);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("message","Order is not valid");
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/cancel-order", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> cancelOrder(@RequestBody BigInteger orderId) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderId!=null) {
			responseMap = orderManagementService.cancelOrder(orderId);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("message","Order is not valid");
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/check-order-status", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> checkOrderStatus(@RequestBody BigInteger orderId) {

		Map<String,Object> responseMap = new HashMap<>();
		if(orderId!=null){
			responseMap = orderManagementService.checkOrderStatus(orderId);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		responseMap.put("message","Order is not valid");
		return responseMap;
	}

	@RequestMapping(value="/list-all-orders", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> listAllOrders() {
		
		Map<String,Object> responseMap = new HashMap<>();
		responseMap = orderManagementService.listAllOrders();
		return responseMap;
	}



}
