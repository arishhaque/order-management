package com.spring.order.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.spring.order.vo.ItemVo;

public interface ItemService {

	public Map<String,Object> create(ItemVo itemVo);
	public Map<String,Object> createBatch(List<ItemVo> itemVos);
	public Map<String,Object> update(ItemVo itemVo);
	public Map<String,Object> updateBatch(List<ItemVo> itemVos);
	public Map<String,Object> read(BigInteger id);
	public Map<String,Object> delete(BigInteger id);
	public Map<String,Object> list();
}
