package com.spring.order.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.spring.order.vo.OrderItemsVo;

public interface OrderItemsService {

	public Map<String,Object> create(OrderItemsVo orderItemsVo);
	public Map<String,Object> createBatch(List<OrderItemsVo> orderItemsVos);
	public Map<String,Object> update(OrderItemsVo orderItemsVo);
	public Map<String,Object> updateBatch(List<OrderItemsVo> orderItemsVos);
	public Map<String,Object> read(BigInteger id);
	public Map<String,Object> delete(BigInteger id);
	public Map<String,Object> list();
}
