package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public interface OrderItemService {

	public List<OrderItem> findAll();
	
	public OrderItem find(Long OrderItemid);
	
	public List<OrderItem> findByOrderItem(String Quantity, String TotalPrice);
	
	public void addOrderItem(OrderItem orderItem);
	
	public void upsertOrderItem(OrderItem orderItem);
	
	public void deleteOrderItem(Long OrderItemid);
}
