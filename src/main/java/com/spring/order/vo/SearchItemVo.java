package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class SearchItemVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger itemId;
	private BigInteger quantity;
	private String orderStatus;
	
	public SearchItemVo() {
		super();
	}

	

	public SearchItemVo(BigInteger itemId, BigInteger quantity, String orderStatus) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
		this.orderStatus = orderStatus;
	}



	public BigInteger getItemId() {
		return itemId;
	}

	public void setItemId(BigInteger itemId) {
		this.itemId = itemId;
	}

	public BigInteger getQuantity() {
		return quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}



	public String getOrderStatus() {
		return orderStatus;
	}



	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
}
