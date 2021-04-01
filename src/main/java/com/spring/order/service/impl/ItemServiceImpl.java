package com.spring.order.service.impl;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.order.dao.ItemDao;
import com.spring.order.model.Item;
import com.spring.order.service.ItemService;
import com.spring.order.vo.ItemVo;

@Service
public class ItemServiceImpl implements ItemService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ItemDao itemDao;
	
	@Override
	public Map<String, Object> create(ItemVo itemVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(itemVo != null) {
			
			Item item = new Item();
			item.setDescription(itemVo.getDescription());
			if(itemVo.getPrice() == null || itemVo.getPrice().compareTo(new BigInteger("0")) < 1) {
				
				logger.info("Item price not specified");
				responseMap.put("status", "success");
				responseMap.put("message", "Please, specify correct item price");
				return responseMap;
			}
			item.setPrice(itemVo.getPrice());
			item.setItemCount(itemVo.getItemCount()!=null ? itemVo.getItemCount():new BigInteger("1"));
			item.setActive(itemVo.getActive() != null ? itemVo.getActive():true);
			item.setStartDate(itemVo.getStartDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(itemVo.getStartDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());
			
			BigInteger id = (BigInteger) (itemDao).create(item);
			if(id != null) {
				
				logger.info("Item added successfully");
				responseMap.put("status", "success");
				responseMap.put("message", "Item added successfully");
				responseMap.put("id", id);
				return responseMap;
				
			}
			
		}
		logger.error("Error: cannot add Item");
		responseMap.put("status", "error");
		responseMap.put("message", "cannot add Item");
		return responseMap;
	
	}

	@Override
	public Map<String, Object> createBatch(List<ItemVo> itemVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<Item> items = new ArrayList<>();
		for(ItemVo itemVo : itemVos) {
			
			Item item = new Item();
			if(itemVo.getPrice() == null || itemVo.getPrice().compareTo(new BigInteger("0")) < 1) {
				
				logger.info("Item price not specified");
				responseMap.put("status", "success");
				responseMap.put("message", "Please, specify correct item price");
				return responseMap;
			}
			item.setPrice(itemVo.getPrice());
			item.setItemCount(itemVo.getItemCount()!=null ? itemVo.getItemCount():new BigInteger("1"));
			item.setDescription(itemVo.getDescription());
			item.setActive(itemVo.getActive() != null ? itemVo.getActive():true);
			item.setStartDate(itemVo.getStartDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(itemVo.getStartDate()),TimeZone.getDefault().toZoneId()) : LocalDateTime.now());
			
			items.add(item);
			
			
		}
		if(items!=null && !items.isEmpty()) {
			List<BigInteger> ids = itemDao.createBatch(items);
			if(ids != null) {
				
				logger.info("Item added successfully");
				responseMap.put("status", "success");
				responseMap.put("message", "Item added successfully");
				responseMap.put("ids", ids);
				return responseMap;
				
			}
		}
		logger.error("Error: cannot add Item");
		responseMap.put("status", "error");
		responseMap.put("message", "cannot add Item");
		return responseMap;
	}

	@Override
	public Map<String, Object> update(ItemVo itemVo) {
		
		Map<String,Object> responseMap = new HashMap<>();
		BigInteger id = itemVo.getId();
		if(itemVo != null &&  id != null) {
			
			Item item = (Item) itemDao.read(id);
			if(item != null) {
				
				if(itemVo.getPrice() == null || itemVo.getPrice().compareTo(new BigInteger("0")) < 1) {
					
					logger.info("Item price not specified");
					responseMap.put("status", "success");
					responseMap.put("message", "Please, specify correct item price");
					return responseMap;
				}
				item.setPrice(itemVo.getPrice());
				item.setItemCount(itemVo.getItemCount()!=null ? itemVo.getItemCount():new BigInteger("1"));
				item.setDescription(itemVo.getDescription()!=null && !itemVo.getDescription().trim().isEmpty() 
						? itemVo.getDescription(): item.getDescription());
				item.setActive(itemVo.getActive() != null ? itemVo.getActive():item.getActive());
				item.setStartDate(itemVo.getStartDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(itemVo.getStartDate()),TimeZone.getDefault().toZoneId()) : item.getStartDate());
				
				itemDao.update(item);
				logger.info("Item updated successfully");
				responseMap.put("status", "success");
				responseMap.put("message", "Item updated successfully");
				return responseMap;
			
			}
					
		}
		logger.error("Error: cannot update Item");
		responseMap.put("status", "error");
		responseMap.put("message", "cannot update Item");
		return responseMap;
	}

	@Override
	public Map<String, Object> updateBatch(List<ItemVo> itemVos) {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<Item> items = new ArrayList<>();
		
		if(itemVos != null && !itemVos.isEmpty()) {
			for(ItemVo itemVo : itemVos) {
				
				if(itemVo != null && itemVo.getId() != null) {
					BigInteger id = itemVo.getId();
					Item item = (Item) itemDao.read(id);
					if(item != null) {
						
						if(itemVo.getPrice() == null || itemVo.getPrice().compareTo(new BigInteger("0")) < 1) {
							
							logger.info("Item price not specified");
							responseMap.put("status", "success");
							responseMap.put("message", "Please, specify correct item price");
							return responseMap;
						}
						item.setPrice(itemVo.getPrice());
						item.setItemCount(itemVo.getItemCount()!=null ? itemVo.getItemCount():new BigInteger("1"));
						item.setDescription(itemVo.getDescription()!=null && !itemVo.getDescription().trim().isEmpty() 
								? itemVo.getDescription(): item.getDescription());
						item.setActive(itemVo.getActive() != null ? itemVo.getActive():item.getActive());
						item.setStartDate(itemVo.getStartDate() != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(itemVo.getStartDate()),TimeZone.getDefault().toZoneId()) : item.getStartDate());
						
						items.add(item);
					}
				}else {
					
					logger.error("Item id not found");
					responseMap.put("status", "error");
					responseMap.put("message", "Item id not found");
					return responseMap;
				}
			}
			itemDao.updateBatch(items);
			logger.info("Items updated successfully");
			responseMap.put("status", "success");
			responseMap.put("message", "Items updated successfully");
			return responseMap;
		}
					
		
		logger.error("Error: cannot update Items");
		responseMap.put("status", "error");
		responseMap.put("message", "cannot update Items");
		return responseMap;
	}

	@Override
	public Map<String, Object> read(BigInteger id) {
		
		Map<String,Object> responseMap = new HashMap<>();
		if(id != null) {
			
			Item item = (Item) itemDao.read(id);
			if(item != null) {
				
				ItemVo itemVo = new ItemVo();
				itemVo.setId(item.getId());
				itemVo.setItemCount(item.getItemCount());
				itemVo.setPrice(item.getPrice());
				itemVo.setActive(item.getActive());
				itemVo.setDescription(item.getDescription());
				itemVo.setStartDate(item.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
				itemVo.setEndDate(item.getEndDate()!=null ? item.getEndDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli():null);
				
				logger.info("Orders found with id"+id);
				responseMap.put("status","success");
				responseMap.put("message","Order found with id");
				responseMap.put("object", itemVo);
				return responseMap;
			}
		}
		
		logger.error("Item not be found:"+id);
		responseMap.put("status", "error");
		responseMap.put("message", "Cannot find Item"+id);
		return responseMap;
	
	}

	@Override
	public Map<String, Object> list() {
		
		Map<String,Object> responseMap = new HashMap<>();
		List<ItemVo> itemVos = new ArrayList<>();
		
		List<Item> items = itemDao.findAllItems();
		if(items != null && !items.isEmpty()) {
				
			for(Item item : items) {
					
				ItemVo itemVo = new ItemVo();
				itemVo.setId(item.getId());
				itemVo.setPrice(item.getPrice());
				itemVo.setActive(item.getActive());
				itemVo.setDescription(item.getDescription());
				itemVo.setStartDate(item.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
				itemVo.setEndDate(item.getEndDate()!=null
						?item.getEndDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli():null);
				
				itemVos.add(itemVo);
			}
				
			logger.info("Items found");
			responseMap.put("status","success");
			responseMap.put("message","Items found");
			responseMap.put("list", itemVos);
			return responseMap;
		}
		
		
		logger.error("Item cannot be found:");
		responseMap.put("status", "error");
		responseMap.put("message", "Error: Cannot find item");
		return responseMap;
	
	}

	@Override
	public Map<String, Object> delete(BigInteger id) {
		
		Map<String,Object> responseMap = new HashMap<>();
		
		if(id != null) {
			
			Item item = (Item) itemDao.read(id);
			if(item!=null) {
				
				item.setActive(false);
				
				logger.info("Item updated with id:"+id);
				itemDao.update(item);
				logger.info("Order updated with id:"+id);
				responseMap.put("status","success");
				responseMap.put("message","Order updated successfully"+id);
				responseMap.put("id",id);
				return responseMap;
			}
		}
		
		logger.error("Item not found");
		responseMap.put("status", "error");
		responseMap.put("message", "Item not found");
		return responseMap;
	
	}

	
}
