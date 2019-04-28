package com.ibm.training.bootcamp.rest.sample01.domain;

public class OrderItem {

	Long OrderItemid;
	private String Quantity;
	private String TotalPrice;

	
	public OrderItem() {
		
	}
	
	public OrderItem(String Quantity, String TotalPrice) {
		this(null, Quantity, TotalPrice);
	}
	
	public OrderItem(Long OrderItemid, String Quantity, String TotalPrice) {
		this.OrderItemid = OrderItemid;
		this.Quantity = Quantity;
		this.TotalPrice = TotalPrice;
	}

	public Long getOrderItemid() {
		return OrderItemid;
	}

	public void setOrderItemid(Long orderItemid) {
		OrderItemid = orderItemid;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		TotalPrice = totalPrice;
	}
	
	
}
