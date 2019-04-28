package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Food;

public interface FoodService {

	public List<Food> findAll();
	
	public Food find(Long id);
	
	public List<Food> findByDelivery(String FoodItemName, String UnitPrice, String InStock);
	
	public void add(Food food);
	
	public void upsert(Food food);
	
	public void delete(Long id);

}
