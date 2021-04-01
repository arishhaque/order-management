package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class ItemVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String description;
	private BigInteger itemCount;
	private BigInteger price;
	private Boolean active;
	private Long startDate;
	private Long endDate;
	
	public ItemVo() {
		super();
	}

	
	
	public ItemVo(BigInteger id) {
		super();
		this.id = id;
	}

	public ItemVo(String description, BigInteger itemCount, BigInteger price, Boolean active, Long startDate,
			Long endDate) {
		super();
		this.description = description;
		this.itemCount = itemCount;
		this.price = price;
		this.active = active;
		this.startDate = startDate;
		this.endDate = endDate;
	}



	public BigInteger getId() {
		return id;
	}



	public void setId(BigInteger id) {
		this.id = id;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigInteger getItemCount() {
		return itemCount;
	}

	public void setItemCount(BigInteger itemCount) {
		this.itemCount = itemCount;
	}
	
	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	
	
	
}
