package com.spring.order.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.spring.order.vo.ItemStatusVo;
import com.spring.order.vo.OrderItemStatusVo;
import com.spring.order.vo.PlaceOrderVo;

public interface OrderManagementService {
	
	Map<String,Object> placeOrder(PlaceOrderVo placeOrderVo);
	Map<String,Object> getOrderDetailsById(BigInteger orderId);
	Map<String,Object> updateOrderStatus(BigInteger orderId,  List<ItemStatusVo> orderItemStatusVos);
	Map<String,Object> checkOrderStatus(OrderItemStatusVo orderItemStatusVo);
	Map<String,Object> listAllOrderDetails();

}
