package com.spring.order.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.spring.order.vo.OrderVo;

public interface OrderService {

	public Map<String,Object> create(OrderVo orderVo);
	public Map<String,Object> createBatch(List<OrderVo> orderVos);
	public Map<String,Object> update(OrderVo orderVo);
	public Map<String,Object> updateBatch(List<OrderVo> orderVos);
	public Map<String,Object> read(BigInteger id);
	public Map<String,Object> delete(BigInteger id);
	public Map<String,Object> list();
}
