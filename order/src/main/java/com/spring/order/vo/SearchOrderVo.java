package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class SearchOrderVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger orderId;
	private String emailId;
	private String description;
	private List<SearchItemVo> itemDetails;
	private BigInteger price;
	
	public SearchOrderVo() {
		super();
	}

	public SearchOrderVo(BigInteger orderId, String emailId, String description, List<SearchItemVo> itemDetails, BigInteger price) {
		super();
		this.orderId = orderId;
		this.emailId = emailId;
		this.description = description;
		this.itemDetails = itemDetails;
		this.price = price;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public List<SearchItemVo> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<SearchItemVo> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}
	

}
