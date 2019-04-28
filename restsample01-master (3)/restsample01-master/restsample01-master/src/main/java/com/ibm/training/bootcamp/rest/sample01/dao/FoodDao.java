package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Food;

public interface FoodDao {
	
	public Food find(Long id);
	
	public List<Food> findAll(); 
	
	public List<Food> findByDelivery(String FoodItemName, String UnitPrice, String InStock);
	
	//public List<FoodDelivery> findByOrder(String CustomerName, String Address, String ContactNumber);

	//public List<FoodDelivery> findByDelivery(String FoodItemName, DecimalFormat UnitPrice, boolean InStock);

	public void add(Food food);
	
	public void update(Food food);
	
	public void delete(Long id);
	
	
	
//	public List<User> findAll();
//	
//	public User find(Long id);
//	
//	public List<User> findByName(String firstName, String lastName);
//	
//	public void add(User user);
//	
//	public void update(User user);
//	
//	public void delete(Long id);

}
