package com.spring.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.order.service.OrderItemsService;
import com.spring.order.vo.OrderItemsVo;


@RestController
@RequestMapping(value="/rest/api/order-items")
public class OrderItemsController {
	
	@Autowired
	private OrderItemsService orderItemsService;
	

	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> create(@RequestBody OrderItemsVo orderItemsVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderItemsVo != null) {
			responseMap = orderItemsService.create(orderItemsVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		return responseMap;
	}
	
	@RequestMapping(value="/create-batch", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> createBatch(@RequestBody List<OrderItemsVo> orderItemsVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderItemsVos != null && !orderItemsVos.isEmpty()) {
			responseMap = orderItemsService.createBatch(orderItemsVos);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		return responseMap;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> update(@RequestBody OrderItemsVo orderItemsVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderItemsVo!=null) {
			responseMap = orderItemsService.update(orderItemsVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/update-batch", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> update(@RequestBody List<OrderItemsVo> orderItemsVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderItemsVo!=null && !orderItemsVo.isEmpty()) {
			responseMap = orderItemsService.updateBatch(orderItemsVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/read", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> read(@RequestBody OrderItemsVo orderItemsVo) {

		Map<String,Object> responseMap = null;
		if(orderItemsVo.getId()!=null){
			responseMap = orderItemsService.read(orderItemsVo.getId());
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap = new HashMap<>();
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/list", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> list() {
		
		Map<String,Object> responseMap = new HashMap<>();
		responseMap = orderItemsService.list();
		return responseMap;
	}


	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteTeam(@RequestBody OrderItemsVo orderItemsVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		responseMap = orderItemsService.delete(orderItemsVo.getId());
		if(responseMap != null && responseMap.get("status").equals("success")) {
			return responseMap;
		}
		return responseMap;
	}
}
