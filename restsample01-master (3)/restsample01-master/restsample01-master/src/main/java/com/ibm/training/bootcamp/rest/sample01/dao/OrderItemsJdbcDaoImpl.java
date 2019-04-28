package com.ibm.training.bootcamp.rest.sample01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.jdbc.JDBCDataSource;

import com.ibm.training.bootcamp.rest.sample01.domain.OrderItem;

public class OrderItemsJdbcDaoImpl implements OrderItemsDao{

private static OrderItemsJdbcDaoImpl INSTANCE;
	
	private JDBCDataSource dataSource;
	
	static public OrderItemsJdbcDaoImpl getInstance() {
		
		OrderItemsJdbcDaoImpl instance;
		if(INSTANCE != null) {
			instance = INSTANCE;
		}else {
			instance = new OrderItemsJdbcDaoImpl();
			INSTANCE = instance;
		}
		
		return instance;
	}
	
	private OrderItemsJdbcDaoImpl() {
		init();
	}
	
	private void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:mem:USER");
		dataSource.setUser("username");
		dataSource.setPassword("password");
		
		
		createOrderItemsTbl();
		insertInitOrderItem();
		
//		createOrderTbl();
//		insertInitOrder();
		
		//createOrderItemTbl();
		//insertInitOrderItem();
		
	}
	
	private void createOrderItemsTbl() {
		String createSqlOrderItemsTbl = "CREATE TABLE OrderItemsTbl " 
				+ "(OrderItemid INTEGER IDENTITY PRIMARY KEY, " 
				+ " Quantity VARCHAR(255), "
				+ " TotalPrice VARCHAR(255))";

		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSqlOrderItemsTbl);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void insertInitOrderItem() { 

		addOrderItem(new OrderItem("20","100.98"));
		addOrderItem(new OrderItem("15","303.876"));
		addOrderItem(new OrderItem("8","44.97"));
	}
	

	@Override
	public List<OrderItem> findAll(){
		return findByOrderItem(null,null);
	}
	
	@Override
	//FINDING ID
	public OrderItem find(Long OrderItemid) {

		OrderItem orderItem = null;

		if (OrderItemid != null) {
			String sql = "SELECT OrderItemid, Quantity, TotalPrice FROM OrderItemTbl where OrderItemid = ?";
			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, OrderItemid.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					orderItem = new OrderItem(Long.valueOf(results.getInt("OrderItemid")), results.getString("Quantity"),
							results.getString("TotalPrice"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return orderItem;
	}
	

	
	@Override
	public List<OrderItem> findByOrderItem(String Quantity, String TotalPrice) {
		List<OrderItem> orderItems = new ArrayList<>();

		String sql = "SELECT OrderItemid, Quantity, TotalPrice FROM OrderItemTbl WHERE Quantity LIKE ? AND TotalPrice LIKE ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(Quantity));
			ps.setString(2, createSearchValue(TotalPrice));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				OrderItem orderItem = new OrderItem(Long.valueOf(results.getInt("OrderItemid")), results.getString("Quantity"),
						results.getString("TotalPrice"));
				orderItems.add(orderItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return orderItems;
	}
	
	
	private String createSearchValue(String string) {
		
		String value;
		
		if (StringUtils.isBlank(string)) {
			value = "%";
		} else {
			value = string;
		}
		
		return value;
	}
	

	@Override
	public void addOrderItem(OrderItem orderItem) {
		
		String insertSql = "INSERT INTO OrderItemTbl (Quantity, TotalPrice) VALUES ( ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, orderItem.getQuantity());
			ps.setString(2, orderItem.getTotalPrice());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		String updateSql = "UPDATE OrderItemTbl SET Quantity = ?, TotalPrice = ? WHERE OrderItemid = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, orderItem.getQuantity());
			ps.setString(2, orderItem.getTotalPrice());
			ps.setLong(3, orderItem.getOrderItemid());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	public void deleteOrderItem(Long OrderItemid) {
		String updateSql = "DELETE FROM OrderItemTbl WHERE  OrderItemid = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1,  OrderItemid);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
