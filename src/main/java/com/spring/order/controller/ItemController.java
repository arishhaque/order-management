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

import com.spring.order.service.ItemService;
import com.spring.order.vo.ItemVo;


@RestController
@RequestMapping(value="/rest/api/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> create(@RequestBody ItemVo itemVo) {
		
		
		Map<String,Object> responseMap = new HashMap<>();
		if(itemVo != null) {
			responseMap = itemService.create(itemVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		return responseMap;
	}
	
	@RequestMapping(value="/create-batch", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> createBatch(@RequestBody List<ItemVo> itemVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(itemVos != null && !itemVos.isEmpty()) {
			responseMap = itemService.createBatch(itemVos);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		return responseMap;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> updateTeam(@RequestBody ItemVo itemVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(itemVo!=null) {
			responseMap = itemService.update(itemVo);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/update-batch", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> update(@RequestBody List<ItemVo> itemVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(itemVos!=null && !itemVos.isEmpty()) {
			responseMap = itemService.updateBatch(itemVos);
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;		
		}
		
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/read", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> readTeam(@RequestBody ItemVo itemVo) {

		Map<String,Object> responseMap = new HashMap<>();
		if(itemVo.getId()!=null){
			responseMap = itemService.read(itemVo.getId());
			if(responseMap != null && responseMap.get("status").equals("success"))
				return responseMap;
		}
		
		responseMap.put("status","error");
		return responseMap;
	}

	@RequestMapping(value="/list")
	public @ResponseBody Map<String,Object> listTeams() {
		
		Map<String,Object> responseMap = new HashMap<>();
		responseMap = itemService.list();
		if(responseMap != null && responseMap.get("status").equals("success")) {
			
			return responseMap;
		}
		return responseMap;
	}


	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteTeam(@RequestBody ItemVo itemVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		responseMap = itemService.delete(itemVo.getId());
		if(responseMap != null && responseMap.get("status").equals("success")) {
			return responseMap;
		}
		return responseMap;
	}
}
