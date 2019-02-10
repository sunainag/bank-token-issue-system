package com.bank.system.model;

public class Customer {

	private int id;
	private String name;
	private Address address;
	private long phoneNumber;
	private ServicePriorityType servicePriority;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ServicePriorityType getServicePriority() {
		return servicePriority;
	}

	public void setServicePriority(ServicePriorityType servicePriority) {
		this.servicePriority = servicePriority;
	}

}
