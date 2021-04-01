package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class PlaceOrderItemVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger itemId;
	private BigInteger quantity;
	
	public PlaceOrderItemVo() {
		super();
	}

	public PlaceOrderItemVo(BigInteger itemId, BigInteger quantity) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
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
	

}
