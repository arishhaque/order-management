package com.spring.order.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.order.service.OrderManagementService;
import com.spring.order.vo.ItemStatusVo;
import com.spring.order.vo.OrderItemStatusVo;
import com.spring.order.vo.OrderStatusVo;
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
	
	@RequestMapping(value="/place-bulk-orders", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> placeBulkOrders(@RequestBody List<PlaceOrderVo> placeOrderVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(placeOrderVos != null  && !placeOrderVos.isEmpty()) {
			responseMap = orderManagementService.placeBulkOrders(placeOrderVos);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		responseMap.put("message","Order could not be placed, pls review your order");
		return responseMap;
	}
	
	@RequestMapping(value="/get-order-details", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> getOrderDetails(@RequestBody Map<String,Object> requestMap) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(requestMap != null && !requestMap.isEmpty() && requestMap.containsKey("orderId")) {
			Integer orderIdInt = (Integer) requestMap.get("orderId");
			BigInteger orderId = BigInteger.valueOf(orderIdInt.intValue());
			responseMap = orderManagementService.getOrderDetailsById(orderId);	
			return responseMap;
		}
		
		responseMap.put("status","error");
		responseMap.put("message","Order is not valid");
		return responseMap;
	}
	
	@RequestMapping(value="/update-order-status", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateOrder(@RequestBody OrderStatusVo orderStatusVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderStatusVo != null && orderStatusVo.getOrderId() != null 
				&& orderStatusVo.getItemStatusVos() != null && !orderStatusVo.getItemStatusVos().isEmpty()) {
			
			BigInteger orderId = orderStatusVo.getOrderId();
			List<ItemStatusVo> orderItemStatusVos =  orderStatusVo.getItemStatusVos();
			responseMap = orderManagementService.updateOrderStatus(orderId, orderItemStatusVos);
			return responseMap;		
		}
		
		responseMap.put("message","Order is not valid");
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/check-order-status", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> checkOrderStatus(@RequestBody OrderItemStatusVo orderItemStatusVo) {

		Map<String,Object> responseMap = new HashMap<>();
		if(orderItemStatusVo != null && orderItemStatusVo.getOrderId() != null 
				&& orderItemStatusVo.getItemIds() != null && !orderItemStatusVo.getItemIds().isEmpty()) {
			
			responseMap = orderManagementService.checkOrderStatus(orderItemStatusVo);
			return responseMap;
		}
		
		responseMap.put("status","error");
		responseMap.put("message","Order is not valid");
		return responseMap;
	}

	@RequestMapping(value="/list-orders-with-search", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> listAllOrders(@RequestBody Map<String,Object> requestMap) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(requestMap != null && !requestMap.isEmpty() && requestMap.containsKey("searchKey")
				&& requestMap.containsKey("pageSize") && requestMap.containsKey("pageNumber")) {
			
			String searchKey = (String) requestMap.get("searchKey");
			Integer pageSize = (Integer) requestMap.get("pageSize");
			Integer pageNumber = (Integer) requestMap.get("pageNumber");
			responseMap = orderManagementService.listOrderDetailsWithSearchKey(searchKey, pageSize, pageNumber);
			return responseMap;
			
		}
		return responseMap;
	}



}
