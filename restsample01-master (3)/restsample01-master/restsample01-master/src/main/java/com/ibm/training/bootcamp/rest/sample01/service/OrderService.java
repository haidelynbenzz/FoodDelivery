package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Order;

public interface OrderService {

	public List<Order> findAllOrder();
	
	public Order findOrder(Long Orderid);
	
	public List<Order> findByOrder(String CustomerName, String Address, String ContactNumber);
	
	public void add(Order order);
	
	public void upsert(Order order);
	
	public void delete(Long Orderid);

}
