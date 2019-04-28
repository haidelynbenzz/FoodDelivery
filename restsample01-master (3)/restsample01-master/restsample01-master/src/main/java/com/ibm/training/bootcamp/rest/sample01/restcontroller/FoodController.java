package com.ibm.training.bootcamp.rest.sample01.restcontroller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.Food;
import com.ibm.training.bootcamp.rest.sample01.service.FoodService;
import com.ibm.training.bootcamp.rest.sample01.service.FoodServiceImpl;

@Path("/AddFoodItem")
public class FoodController {

	private FoodService foodService;

	public FoodController() {
		this.foodService = new FoodServiceImpl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Food> getFoods(
			@QueryParam("FoodItemName") String FoodItemName, 
			@QueryParam("UnitPrice") String UnitPrice,
			@QueryParam("InStock") String InStock) {

		try {
			List<Food> foods;
			
			if (StringUtils.isAllBlank(FoodItemName, UnitPrice, InStock)) {
				foods = foodService.findAll();
			} else {
				foods = foodService.findByDelivery(FoodItemName, UnitPrice, InStock);
			}
						
			return foods;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Food getFood(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			Food food = foodService.find(longId);
			return food;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addFood(Food food) {

		try {
			foodService.add(food);
			String result = "Food Information saved : " + food.getFoodItemName() + " " + food.getUnitPrice() + "  " + food.getInStock();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateFood(@PathParam("id") Long id, Food food) {

		try {
			food.setId(id);
			foodService.upsert(food);
			String result = "Food Item updated : " + id + " " + food.getFoodItemName() + " " + food.getUnitPrice() + " " + food.getInStock();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@DELETE
	@Path("{id}")
	public Response deleteFood(@PathParam("id") String id) {

		try {
			Long longId = Long.parseLong(id);
			foodService.delete(longId);
			String result = "Food Item deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	
	
	
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<User> getUsers(
//			@QueryParam("firstName") String firstName, 
//			@QueryParam("lastName") String lastName) {
//
//		try {
//			List<User> users;
//			
//			if (StringUtils.isAllBlank(firstName, lastName)) {
//				users = userService.findAll();
//			} else {
//				users = userService.findByName(firstName, lastName);
//			}
//						
//			return users;
//			
//		} catch (Exception e) {
//			throw new WebApplicationException(e);
//		}
//
//	}
//
//	@GET
//	@Path("{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public User getUser(@PathParam("id") String id) {
//
//		try {
//			Long longId = Long.parseLong(id);
//			User user = userService.find(longId);
//			return user;
//		} catch (Exception e) {
//			throw new WebApplicationException(e);
//		}
//	}
//
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response addUser(User user) {
//
//		try {
//			userService.add(user);
//			String result = "User saved : " + user.getFirstName() + " " + user.getLastName();
//			return Response.status(201).entity(result).build();
//		} catch (Exception e) {
//			throw new WebApplicationException(e);
//		}
//
//	}
//
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response updateUser(User user) {
//
//		try {
//			userService.upsert(user);
//			String result = "User updated : " + user.getFirstName() + " " + user.getLastName();
//			return Response.status(200).entity(result).build();
//		} catch (Exception e) {
//			throw new WebApplicationException(e);
//		}
//
//	}
//
//	@DELETE
//	@Path("{id}")
//	public Response deleteUser(@PathParam("id") String id) {
//
//		try {
//			Long longId = Long.parseLong(id);
//			userService.delete(longId);
//			String result = "User deleted";
//			return Response.status(200).entity(result).build();
//		} catch (Exception e) {
//			throw new WebApplicationException(e);
//		}
//	}
}
