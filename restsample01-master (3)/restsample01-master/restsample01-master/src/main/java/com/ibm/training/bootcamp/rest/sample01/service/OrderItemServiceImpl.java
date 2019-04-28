package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.dao.OrderItemsDao;
import com.ibm.training.bootcamp.rest.sample01.dao.OrderItemsJdbcDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public class OrderItemServiceImpl  implements OrderItemService{
	
	OrderItemsDao orderItemDao;

	public OrderItemServiceImpl() {
		this.orderItemDao = OrderItemsJdbcDaoImpl.getInstance();
		//this.foodDao = UserHashMapDaoImpl.getInstance();
	}
	

	@Override
	public List<OrderItem> findAll(){
		return orderItemDao.findAll();
	}
	
	public OrderItem find(Long OrderItemid) {
		return orderItemDao.find(OrderItemid);
	}
	
	public List<OrderItem> findByOrderItem(String Quantity, String TotalPrice){
		return orderItemDao.findByOrderItem(Quantity, TotalPrice);
	}
	
	@Override
	public void addOrderItem(OrderItem orderItem) {
		if (validate(orderItem)) {
			orderItemDao.addOrderItem(orderItem);
		} else {
			throw new IllegalArgumentException("Fields Quantity, TotalPrice.");
		}
	}
	
	@Override
	public void upsertOrderItem(OrderItem orderItem) {
		if (validate(orderItem)) {
			if(orderItem.getOrderItemid() != null && orderItem.getOrderItemid() >= 0) {
				System.out.println("Order Item Updated");
				orderItemDao.updateOrderItem(orderItem);
			} else {
				System.out.println("Order Item Added");
				orderItemDao.addOrderItem(orderItem);
			}
		} else {
			throw new IllegalArgumentException("Fields FoodItemName, UnitPrice and InStock cannot be blank.");
		}
	}
	
	@Override
	public void deleteOrderItem(Long OrderItemid) {
		orderItemDao.deleteOrderItem(OrderItemid);
	}
	
	private boolean validate(OrderItem orderItem) {
		return !StringUtils.isAnyBlank(orderItem.getQuantity(), orderItem.getTotalPrice());
	}


}
