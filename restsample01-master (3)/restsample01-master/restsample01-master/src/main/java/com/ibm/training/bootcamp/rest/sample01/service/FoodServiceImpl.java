package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.dao.FoodDao;
//import com.ibm.training.bootcamp.rest.sample01.dao.UserHashMapDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.dao.FoodJdbcDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.domain.Food;

public class FoodServiceImpl implements FoodService{
	
	FoodDao foodDao;

	public FoodServiceImpl() {
		this.foodDao = FoodJdbcDaoImpl.getInstance();
		//this.foodDao = UserHashMapDaoImpl.getInstance();
	}
	

	@Override
	public List<Food> findAll(){
		return foodDao.findAll();
	}
	
	public Food find(Long id) {
		return foodDao.find(id);
	}
	
	public List<Food> findByDelivery(String FoodItemName, String UnitPrice, String InStock){
		return foodDao.findByDelivery(FoodItemName, UnitPrice, InStock);
	}
	
	@Override
	public void add(Food food) {
		if (validate(food)) {
			foodDao.add(food);
		} else {
			throw new IllegalArgumentException("Fields FoodItemName, UnitPrice and InStock cannot be blank.");
		}
	}
	
	@Override
	public void upsert(Food food) {
		if (validate(food)) {
			if(food.getId() != null && food.getId() >= 0) {
				System.out.println("Food Item Updated");
				foodDao.update(food);
			} else {
				System.out.println("Food Item Added");
				foodDao.add(food);
			}
		} else {
			throw new IllegalArgumentException("Fields FoodItemName, UnitPrice and InStock cannot be blank.");
		}
	}
	
	@Override
	public void delete(Long id) {
		foodDao.delete(id);
	}
	
	private boolean validate(Food food) {
		return !StringUtils.isAnyBlank(food.getFoodItemName(), food.getUnitPrice(), food.getInStock());
	}

}
