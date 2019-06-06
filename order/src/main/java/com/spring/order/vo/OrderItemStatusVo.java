package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class OrderItemStatusVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger orderId;
	private List<BigInteger> itemIds;
	
	public OrderItemStatusVo() {
		super();
	}

	public OrderItemStatusVo(BigInteger orderId, List<BigInteger> itemIds) {
		super();
		this.orderId = orderId;
		this.itemIds = itemIds;
	}

	public BigInteger getOrderId() {
		return orderId;
	}

	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}

	public List<BigInteger> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<BigInteger> itemIds) {
		this.itemIds = itemIds;
	}
	
	

}
