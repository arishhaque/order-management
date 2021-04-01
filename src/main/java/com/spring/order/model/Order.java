package com.spring.order.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="orders")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private String emailId;
	private String description;
	private Boolean active;
	private LocalDateTime date;
	
	
	public Order() {
		super();
	}
	public Order(BigInteger id) {
		super();
		this.id = id;
	}
	public Order(String emailId, String description, Boolean active, LocalDateTime date) {
		super();
		this.emailId = emailId;
		this.description = description;
		this.active = active;
		this.date = date;
	}
	
	
	
	public Order(BigInteger id, String emailId, String description, Boolean active, LocalDateTime date) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.description = description;
		this.active = active;
		this.date = date;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	@Column(name = "email_id",nullable = false)
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@Column(name = "description",nullable = false)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "active",nullable = false)
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@Column(name = "date",nullable = false)
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
	
	
}
