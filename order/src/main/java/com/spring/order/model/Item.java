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
@Table(name="item")
public class Item implements Serializable {

	/**
	 * @author arishhaque
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String description;
	private BigInteger itemCount;
	private BigInteger price;
	private Boolean active;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public Item() {
		super();
	}
	
	public Item(BigInteger id) {
		super();
		this.id = id;
	}

	public Item(String description, BigInteger itemCount, BigInteger price, Boolean active, LocalDateTime startDate,
			LocalDateTime endDate) {
		super();
		this.description = description;
		this.itemCount = itemCount;
		this.price = price;
		this.active = active;
		this.startDate = startDate;
		this.endDate = endDate;
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

	
	@Column(name = "description",nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "item_count",nullable = false)
	public BigInteger getItemCount() {
		return itemCount;
	}

	public void setItemCount(BigInteger itemCount) {
		this.itemCount = itemCount;
	}
	
	@Column(name = "price",nullable = false)
	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}

	@Column(name = "active",nullable = false)
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "start_date",nullable = false)
	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date",nullable = true)
	public LocalDateTime getEndDate() {
		return endDate;
	}

	
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	
	
}
