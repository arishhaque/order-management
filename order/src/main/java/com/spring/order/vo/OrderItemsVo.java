package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class OrderItemsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private BigInteger orderId;
	private BigInteger itemId;
	private BigInteger quantity;
	private BigInteger price;
	private String orderStatus;
	private Boolean active;
	private Long date;
	
	public OrderItemsVo() {
		super();
	}

	public OrderItemsVo(BigInteger id) {
		super();
		this.id = id;
	}

	

	public OrderItemsVo(BigInteger orderId, BigInteger itemId, BigInteger quantity, BigInteger price,
			String orderStatus, Boolean active, Long date) {
		super();
		this.orderId = orderId;
		this.itemId = itemId;
		this.quantity = quantity;
		this.price = price;
		this.orderStatus = orderStatus;
		this.active = active;
		this.date = date;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	

	public BigInteger getOrderId() {
		return orderId;
	}

	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
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

	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	
	
	

}
