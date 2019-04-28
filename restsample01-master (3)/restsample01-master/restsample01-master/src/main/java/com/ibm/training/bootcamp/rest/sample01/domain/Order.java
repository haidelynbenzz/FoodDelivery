package com.ibm.training.bootcamp.rest.sample01.domain;

public class Order {
	
	//ORDERTBL FIELDS
	Long Orderid;
	private String CustomerName;
	private String Address;
	private String ContactNumber;
	
	public Order() {
		
	}
	
	//ORDERTBL METHOD
	public Order(String CustomerName, String Address, String ContactNumber) {
		this(null, CustomerName, Address, ContactNumber);
	}
	
	public Order(Long Orderid, String CustomerName, String Address, String ContactNumber) {
		this.Orderid = Orderid;
		this.CustomerName = CustomerName;
		this.Address = Address;
		this.ContactNumber = ContactNumber;
	}
	



	//ORDERTBL GETTERS AND SETTERS
	public Long getOrderid() {
		return Orderid;
	}

	public void setOrderid(Long orderid) {
		Orderid = orderid;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}
}
