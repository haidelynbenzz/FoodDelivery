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

import com.ibm.training.bootcamp.rest.sample01.domain.Order;

public class OrderJdbcDaoImpl implements OrderDao {

	
private static OrderJdbcDaoImpl INSTANCE;
	
	private JDBCDataSource dataSource;
	
	static public OrderJdbcDaoImpl getInstance() {
		
		OrderJdbcDaoImpl instance;
		if(INSTANCE != null) {
			instance = INSTANCE;
		}else {
			instance = new OrderJdbcDaoImpl();
			INSTANCE = instance;
		}
		
		return instance;
	}
	
	private OrderJdbcDaoImpl() {
		init();
	}
	
	private void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:mem:USER");
		dataSource.setUser("username");
		dataSource.setPassword("password");
		
		

		createOrderTbl();
		insertInitOrder();
		
		//createOrderItemTbl();
		//insertInitOrderItem();
		
	}
	

	
	//ORDERTBL & INITORDER
	
	private void createOrderTbl() {
		String createSqlOrderTbl = "CREATE TABLE OrderTbl " 
				+ "(OrderID INTEGER IDENTITY PRIMARY KEY, " 
				+ " CustomerName VARCHAR(255), "
				+ " Address VARCHAR(255), "
				+ " ContactNumber VARCHAR(255))";

		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSqlOrderTbl);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void insertInitOrder() {

		add(new Order("Haide","California","022-011"));
		add(new Order("Steve","LA","033-055"));
	}
	
	@Override
	public List<Order> findAllOrder(){
		return findByOrder(null,null,null);
	}
	
	@Override
	//FINDING ID
	public Order findOrder(Long Orderid) {

		Order order = null;

		if (Orderid != null) {
			String sql = "SELECT Orderid, CustomerName, Address, ContactNumber FROM OrderTbl where Orderid = ?";
			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, Orderid.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					order = new Order(Long.valueOf(results.getInt("Orderid")), results.getString("CustomerName"),
							results.getString("Address"), results.getString("ContactNumber"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return order;
	}
	
	@Override
	public List<Order> findByOrder(String CustomerName, String Address, String ContactNumber) {
		List<Order> orders = new ArrayList<>();

		String sql = "SELECT Orderid, CustomerName, Address, ContactNumber FROM OrderTbl WHERE CustomerName LIKE ? AND Address LIKE ? AND ContactNumber LIKE ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(CustomerName));
			ps.setString(2, createSearchValue(Address));
			ps.setString(3, createSearchValue(ContactNumber));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				Order order = new Order(Long.valueOf(results.getInt("Orderid")), results.getString("CustomerName"),
						results.getString("Address"), results.getString("ContactNumber"));
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return orders;
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
	public void add(Order order) {
		
		String insertSql = "INSERT INTO OrderTbl (CustomerName, Address, ContactNumber) VALUES (?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, order.getCustomerName());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getContactNumber());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Order order) {
		String updateSql = "UPDATE OrderTbl SET CustomerName = ?, Address = ?, ContactNumber = ? WHERE Orderid = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, order.getCustomerName());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getContactNumber());
			ps.setLong(4, order.getOrderid());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	public void delete(Long Orderid) {
		String updateSql = "DELETE FROM OrderTbl WHERE Orderid = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, Orderid);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	


}
