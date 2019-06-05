package com.spring.order.service;

import java.math.BigInteger;
import java.util.Map;

import com.spring.order.vo.PlaceOrderVo;

public interface OrderManagementService {
	
	Map<String,Object> placeOrder(PlaceOrderVo placeOrderVo);
	Map<String,Object> getOrderDetailsById(BigInteger orderId);
	Map<String,Object> updateOrder(BigInteger orderId);
	Map<String,Object> cancelOrder(BigInteger orderId);
	Map<String,Object> checkOrderStatus(BigInteger orderId);
	Map<String,Object> listAllOrders();

}
