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
	private List<PlaceOrderItemVo> placeOrderItemVos;
	private BigInteger price;
	
	public PlaceOrderVo() {
		super();
	}

	

	public PlaceOrderVo(String emailId, String description, List<PlaceOrderItemVo> placeOrderItemVos,
			BigInteger price) {
		super();
		this.emailId = emailId;
		this.description = description;
		this.placeOrderItemVos = placeOrderItemVos;
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

	public List<PlaceOrderItemVo> getPlaceOrderItemVos() {
		return placeOrderItemVos;
	}



	public void setPlaceOrderItemVos(List<PlaceOrderItemVo> placeOrderItemVos) {
		this.placeOrderItemVos = placeOrderItemVos;
	}



	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}	

}
