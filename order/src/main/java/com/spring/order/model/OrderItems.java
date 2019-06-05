package com.spring.order.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="order_items")
public class OrderItems implements Serializable{
	
	/**
	 * @author arishhaque
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private Order order;
	private Item item;
	private BigInteger quantity;
	private BigInteger price;
	private String orderStatus;
	private Boolean active;
	private LocalDateTime date;
	
	public OrderItems() {
		super();
	}

	public OrderItems(BigInteger id) {
		super();
		this.id = id;
	}

	public OrderItems(Order order, Item item, BigInteger quantity, BigInteger price, String orderStatus,
			Boolean active, LocalDateTime date) {
		super();
		this.order = order;
		this.item = item;
		this.quantity = quantity;
		this.price = price;
		this.orderStatus = orderStatus;
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
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="order_id",nullable = false)
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="item_id",nullable = false)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	@Column(name = "quantity",nullable = false)
	public BigInteger getQuantity() {
		return quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	@Column(name = "price",nullable = false)
	public BigInteger getPrice() {
		return price;
	}

	public void setPrice(BigInteger price) {
		this.price = price;
	}

	@Column(name = "order_status",nullable = true)
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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
