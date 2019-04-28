package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Order;

public interface OrderDao {

	public Order findOrder(Long Orderid);
	
	public List<Order> findAllOrder(); 
	
	public List<Order> findByOrder(String CustomerName, String Address, String ContactNumber);
	
	//public List<Order> findByOrderItem(String CustomerName, String Address, String ContactNumber);

	//public List<FoodDelivery> findByDelivery(String FoodItemName, DecimalFormat UnitPrice, boolean InStock);

	public void add(Order order);
	
	public void update(Order order);
	
	public void delete(Long Orderid);
}
