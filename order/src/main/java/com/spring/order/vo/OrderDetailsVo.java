package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class OrderDetailsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger orderId;
	private String emailId;
	private BigInteger itemId;
	private String orderStatus;
	private BigInteger price;
	private BigInteger quantity;
		
	public OrderDetailsVo() {
		super();
	}

	public OrderDetailsVo(BigInteger orderId, String emailId, BigInteger itemId, String orderStatus, BigInteger price,
			BigInteger quantity) {
		super();
		this.orderId = orderId;
		this.emailId = emailId;
		this.itemId = itemId;
		this.orderStatus = orderStatus;
		this.price = price;
		this.quantity = quantity;
	}

	public BigInteger getOrderId() {
		return orderId;
	}

	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}

	public BigInteger getQuantity() {
		return quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

}
