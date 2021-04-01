package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class OrderStatusVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger orderId;
	private List<ItemStatusVo> itemStatusVos;
	
	public OrderStatusVo() {
		super();
	}

	public OrderStatusVo(BigInteger orderId, List<ItemStatusVo> itemStatusVos) {
		super();
		this.orderId = orderId;
		this.itemStatusVos = itemStatusVos;
	}

	public BigInteger getOrderId() {
		return orderId;
	}

	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}

	public List<ItemStatusVo> getItemStatusVos() {
		return itemStatusVos;
	}

	public void setItemStatusVos(List<ItemStatusVo> itemStatusVos) {
		this.itemStatusVos = itemStatusVos;
	}

	
}
