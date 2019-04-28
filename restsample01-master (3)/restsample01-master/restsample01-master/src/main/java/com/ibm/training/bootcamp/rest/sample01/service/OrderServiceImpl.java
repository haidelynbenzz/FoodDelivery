package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.dao.OrderDao;
//import com.ibm.training.bootcamp.rest.sample01.dao.UserHashMapDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.dao.OrderJdbcDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.domain.Order;

public class OrderServiceImpl implements OrderService{
	
	OrderDao orderDao;

	public OrderServiceImpl() {
		this.orderDao = OrderJdbcDaoImpl.getInstance();
		//this.orderDao = UserHashMapDaoImpl.getInstance();
	}
	

	@Override
	public List<Order> findAllOrder(){
		return orderDao.findAllOrder();
	}
	
	public Order findOrder(Long Orderid) {
		return orderDao.findOrder(Orderid);
	}
	
	public List<Order> findByOrder(String CustomerName, String Address, String ContactNumber){
		return orderDao.findByOrder(CustomerName, Address, ContactNumber);
	}
	
	@Override
	public void add(Order order) {
		if (validate(order)) {
			orderDao.add(order);
		} else {
			throw new IllegalArgumentException("Fields CustomerName, Address and ContactNumber cannot be blank.");
		}
	}
	
	@Override
	public void upsert(Order order) {
		if (validate(order)) {
			if(order.getOrderid() != null && order.getOrderid() >= 0) {
				orderDao.update(order);
			} else {
				orderDao.add(order);
			}
		} else {
			throw new IllegalArgumentException("Fields CustomerName, Address and ContactNumber cannot be blank.");
		}
	}
	
	@Override
	public void delete(Long id) {
		orderDao.delete(id);
	}
	
	private boolean validate(Order order) {
		return !StringUtils.isAnyBlank(order.getCustomerName(), order.getAddress(), order.getContactNumber());
	}

}
