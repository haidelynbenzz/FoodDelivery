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

import com.ibm.training.bootcamp.rest.sample01.domain.Food;

public class FoodJdbcDaoImpl implements FoodDao {

	
private static FoodJdbcDaoImpl INSTANCE;
	
	private JDBCDataSource dataSource;
	
	static public FoodJdbcDaoImpl getInstance() {
		
		FoodJdbcDaoImpl instance;
		if(INSTANCE != null) {
			instance = INSTANCE;
		}else {
			instance = new FoodJdbcDaoImpl();
			INSTANCE = instance;
		}
		
		return instance;
	}
	
	private FoodJdbcDaoImpl() {
		init();
	}
	
	private void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:mem:USER");
		dataSource.setUser("username");
		dataSource.setPassword("password");
		
		
		createFoodItemTbl();
		insertInitFoodItem();
		
//		createOrderTbl();
//		insertInitOrder();
		
		//createOrderItemTbl();
		//insertInitOrderItem();
		
	}
	
	private void createFoodItemTbl() {
		String createSqlFoodItemTbl = "CREATE TABLE FoodItemTbl " 
				+ "(id INTEGER IDENTITY PRIMARY KEY, " 
				+ " FoodItemName VARCHAR(255), "
				+ " UnitPrice VARCHAR(255)," 
				+ " InStock VARCHAR(255))";

		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSqlFoodItemTbl);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void insertInitFoodItem() { 

		add(new Food("Haide","1011.1119","True"));
		add(new Food("Steve","9911.3114","False"));
		add(new Food("Parker","9911.3114","True"));
	}
	
	
	//ORDERTBL & INITORDER
	
//	private void createOrderTbl() {
//		String createSqlOrderTbl = "CREATE TABLE OrderTbl " 
//				+ "(OrderID INTEGER IDENTITY PRIMARY KEY, " 
//				+ " CustomerName VARCHAR(255), "
//				+ " Address VARCHAR(255), "
//				+ " ContactNumber VARCHAR(255))";
//
//		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
//
//			stmt.executeUpdate(createSqlOrderTbl);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	private void insertInitOrder() {
//
//		add(new FoodDelivery("Haide","California","022-011"));
//		add(new FoodDelivery("Steve","LA","033-055"));
//	}
	
	@Override
	public List<Food> findAll(){
		return findByDelivery(null,null,null);
	}
	
	@Override
	//FINDING ID
	public Food find(Long id) {

		Food food = null;

		if (id != null) {
			String sql = "SELECT id, FoodItemName, UnitPrice, InStock FROM FoodItemTbl where id = ?";
			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, id.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					food = new Food(Long.valueOf(results.getInt("id")), results.getString("FoodItemName"),
							results.getString("UnitPrice"), results.getString("InStock"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return food;
	}
	
	@Override
	public List<Food> findByDelivery(String FoodItemName, String UnitPrice, String InStock) {
		List<Food> foods = new ArrayList<>();

		String sql = "SELECT id, FoodItemName, UnitPrice, InStock FROM FoodItemTbl WHERE FoodItemName LIKE ? AND UnitPrice LIKE ? AND InStock LIKE ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(FoodItemName));
			ps.setString(2, createSearchValue(UnitPrice));
			ps.setString(3, createSearchValue(InStock));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				Food food = new Food(Long.valueOf(results.getInt("id")), results.getString("FoodItemName"),
						results.getString("UnitPrice"), results.getString("InStock"));
				foods.add(food);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return foods;
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
	public void add(Food food) {
		
		String insertSql = "INSERT INTO FoodItemTbl (FoodItemName, UnitPrice, InStock) VALUES (?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, food.getFoodItemName());
			ps.setString(2, food.getUnitPrice());
			ps.setString(3, food.getInStock());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Food food) {
		String updateSql = "UPDATE FoodItemTbl SET FoodItemName = ?, UnitPrice = ?, InStock = ? WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, food.getFoodItemName());
			ps.setString(2, food.getUnitPrice());
			ps.setString(3, food.getInStock());
			ps.setLong(4, food.getId());
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	@Override
	public void delete(Long id) {
		String updateSql = "DELETE FROM FoodItemTbl WHERE id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
//	private static UserJdbcDaoImpl INSTANCE;
//
//	private JDBCDataSource dataSource;
//
//	static public UserJdbcDaoImpl getInstance() {
//
//		UserJdbcDaoImpl instance;
//		if (INSTANCE != null) {
//			instance = INSTANCE;
//		} else {
//			instance = new UserJdbcDaoImpl();
//			INSTANCE = instance;
//		}
//
//		return instance;
//	}
//
//	private UserJdbcDaoImpl() {
//		init();
//	}
//
//	private void init() {
//		dataSource = new JDBCDataSource();
//		dataSource.setDatabase("jdbc:hsqldb:mem:USER");
//		dataSource.setUser("username");
//		dataSource.setPassword("password");
//
//		createUserTable();
//		insertInitUsers();
//
//	}
//
//	private void createUserTable() {
//		String createSql = "CREATE TABLE USERS " + "(id INTEGER IDENTITY PRIMARY KEY, " + " firstname VARCHAR(255), "
//				+ " lastname VARCHAR(255))";
//
//		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
//
//			stmt.executeUpdate(createSql);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	private void insertInitUsers() {
//
//		add(new User("Tony","Stark"));
//		add(new User("Steve","Rogers"));
//		add(new User("Peter","Parker"));
//		add(new User("Natasha","Romanov"));
//	}
//
//	@Override
//	public List<User> findAll() {
//
//		return findByName(null, null);
//	}
//
//	@Override
//	public User find(Long id) {
//
//		User user = null;
//
//		if (id != null) {
//			String sql = "SELECT id, firstname, lastname FROM USERS where id = ?";
//			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//				ps.setInt(1, id.intValue());
//				ResultSet results = ps.executeQuery();
//
//				if (results.next()) {
//					user = new User(Long.valueOf(results.getInt("id")), results.getString("firstname"),
//							results.getString("lastname"));
//				}
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//				throw new RuntimeException(e);
//			}
//		}
//
//		return user;
//	}
//
//	@Override
//	public List<User> findByName(String firstName, String lastName) {
//		List<User> users = new ArrayList<>();
//
//		String sql = "SELECT id, firstname, lastname FROM USERS WHERE firstname LIKE ? AND lastname LIKE ?";
//
//		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//			ps.setString(1, createSearchValue(firstName));
//			ps.setString(2, createSearchValue(lastName));
//			
//			ResultSet results = ps.executeQuery();
//
//			while (results.next()) {
//				User user = new User(Long.valueOf(results.getInt("id")), results.getString("firstname"),
//						results.getString("lastname"));
//				users.add(user);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//
//		return users;
//	}
//
//	private String createSearchValue(String string) {
//		
//		String value;
//		
//		if (StringUtils.isBlank(string)) {
//			value = "%";
//		} else {
//			value = string;
//		}
//		
//		return value;
//	}
//	
//	@Override
//	public void add(User user) {
//		
//		String insertSql = "INSERT INTO USERS (firstname, lastname) VALUES (?, ?)";
//
//		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {
//
//			ps.setString(1, user.getFirstName());
//			ps.setString(2, user.getLastName());
//			ps.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	public void update(User user) {
//		String updateSql = "UPDATE users SET firstname = ?, lastname = ? WHERE id = ?";
//
//		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {
//
//			ps.setString(1, user.getFirstName());
//			ps.setString(2, user.getLastName());
//			ps.setLong(3, user.getId());
//			ps.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//	public void delete(Long id) {
//		String updateSql = "DELETE FROM users WHERE id = ?";
//
//		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {
//
//			ps.setLong(1, id);
//			ps.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}

}
