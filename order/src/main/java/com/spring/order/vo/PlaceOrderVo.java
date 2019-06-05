package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class PlaceOrderVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String emailId;
	private String description;
	private List<BigInteger> itemIds;
	private BigInteger quantity;
	private BigInteger price;
	
	public PlaceOrderVo() {
		super();
	}

	public PlaceOrderVo(String emailId, String description, List<BigInteger> itemIds, BigInteger quantity,
			BigInteger price) {
		super();
		this.emailId = emailId;
		this.description = description;
		this.itemIds = itemIds;
		this.quantity = quantity;
		this.price = price;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BigInteger> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<BigInteger> itemIds) {
		this.itemIds = itemIds;
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

}
