package com.bank.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "customer",uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"), @UniqueConstraint(columnNames = "phone_number")})
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "first_name", unique = false, nullable = false, length = 100)
    private String firstName;
 
    @Column(name = "last_name", unique = false, nullable = false, length = 100)
    private String lastName;
	
	@Column
	private String address;//Later, can be replaced by complex object Address
	
	@Column(name = "phone_number", nullable = false)
	private long phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column
	private ServicePriorityType servicePriority;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
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
