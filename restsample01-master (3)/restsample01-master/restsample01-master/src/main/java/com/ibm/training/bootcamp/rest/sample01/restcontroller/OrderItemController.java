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

import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;
import com.ibm.training.bootcamp.rest.sample01.service.OrderItemService;
import com.ibm.training.bootcamp.rest.sample01.service.OrderItemServiceImpl;

@Path("/OrderItem")
public class OrderItemController {


	private OrderItemService orderItemService;

	public OrderItemController() {
		this.orderItemService = new OrderItemServiceImpl();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderItem> getOrderItems(
			@QueryParam("Quantity") String Quantity, 
			@QueryParam("TotalPrice") String TotalPrice){

		try {
			List<OrderItem> orderItems;
			
			if (StringUtils.isAllBlank(Quantity, TotalPrice)) {
				orderItems = orderItemService.findAll();
			} else {
				orderItems = orderItemService.findByOrderItem(Quantity, TotalPrice);
			}
						
			return orderItems;
			
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@GET
	@Path("{OrderItemid}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderItem getOrderItem(@PathParam("OrderItemid") String OrderItemid) {

		try {
			Long longId = Long.parseLong(OrderItemid);
			OrderItem orderItem = orderItemService.find(longId);
			return orderItem;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrderItem(OrderItem orderItem) {

		try {
			orderItemService.addOrderItem(orderItem);
			String result = "OrderItem Information saved : " + orderItem.getQuantity() + " " + orderItem.getTotalPrice();
			return Response.status(201).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@PUT
	@Path("{OrderItemid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrderItem(@PathParam("OrderItemid") Long OrderItemid, OrderItem orderItem) {

		try {
			orderItem.setOrderItemid(OrderItemid);
			orderItemService.upsertOrderItem(orderItem);
			String result = "OrderItem Item updated : " + OrderItemid + " " + orderItem.getQuantity() + " " + orderItem.getTotalPrice();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

	}
	
	@DELETE
	@Path("{OrderItemid}")
	public Response deleteOrderItem(@PathParam("OrderItemid") String OrderItemid) {

		try {
			Long longId = Long.parseLong(OrderItemid);
			orderItemService.deleteOrderItem(longId);
			String result = "OrderItem Item deleted";
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
