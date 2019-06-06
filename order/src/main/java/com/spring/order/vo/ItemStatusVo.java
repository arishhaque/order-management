package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class ItemStatusVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger itemId;
	private String orderStatus;
	
	public ItemStatusVo() {
		super();
	}

	public ItemStatusVo(BigInteger itemId, String orderStatus) {
		super();
		this.itemId = itemId;
		this.orderStatus = orderStatus;
	}

	public BigInteger getItemId() {
		return itemId;
	}

	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	

}
