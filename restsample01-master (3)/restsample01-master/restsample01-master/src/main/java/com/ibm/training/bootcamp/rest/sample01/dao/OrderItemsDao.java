package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public interface OrderItemsDao {

public OrderItem find(Long OrderItemid);
	
	public List<OrderItem> findAll(); 
	
	public List<OrderItem> findByOrderItem(String Quantity, String TotalPrice);
	
	public void addOrderItem(OrderItem orderItem);
	
	public void updateOrderItem(OrderItem orderItem);
	
	public void deleteOrderItem(Long OrderItemid);

}
