package com.spring.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.order.service.OrderService;
import com.spring.order.vo.OrderVo;

@RestController
@RequestMapping(value="/rest/api/orders")
public class OrderController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> create(@RequestBody OrderVo orderVo) {
		
		logger.info("hello!");
		Map<String,Object> responseMap = new HashMap<>();
		if(orderVo != null) {
			responseMap = orderService.create(orderVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		return responseMap;
	}
	
	@RequestMapping(value="/create-batch", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> createBatch(@RequestBody List<OrderVo> orderVos) {
		
		logger.info("hello!");
		Map<String,Object> responseMap = new HashMap<>();
		if(orderVos != null && !orderVos.isEmpty()) {
			responseMap = orderService.createBatch(orderVos);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		return responseMap;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> update(@RequestBody OrderVo orderVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderVo!=null) {
			responseMap = orderService.update(orderVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/update-batch", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> update(@RequestBody List<OrderVo> orderVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(orderVos!=null && !orderVos.isEmpty()) {
			responseMap = orderService.updateBatch(orderVos);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/read", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> read(@RequestBody OrderVo orderVo) {

		Map<String,Object> responseMap = null;
		if(orderVo.getId()!=null){
			responseMap = orderService.read(orderVo.getId());
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
		responseMap = orderService.list();
		return responseMap;
	}


	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteTeam(@RequestBody OrderVo orderVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		responseMap = orderService.delete(orderVo.getId());
		if(responseMap != null && responseMap.get("status").equals("success")) {
			return responseMap;
		}
		return responseMap;
	}


}
