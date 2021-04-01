package com.spring.order.vo;

import java.io.Serializable;
import java.math.BigInteger;


public class OrderVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String emailId;
	private String description;
	private Boolean active;
	private Long date;
	
	public OrderVo() {
		super();
	}

	public OrderVo(BigInteger id) {
		super();
		this.id = id;
	}

	

	public OrderVo(String emailId, String description, Boolean active, Long date) {
		super();
		this.emailId = emailId;
		this.description = description;
		this.active = active;
		this.date = date;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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
