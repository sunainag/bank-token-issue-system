package com.abs.banking.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "customer")
public class Customer {

	public enum CustomerType {
		PREMIUM, REGULAR
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	@NotNull
	@Column(unique = true)
	private String mobile;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CustomerType type = CustomerType.REGULAR;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id")
	private Address address;

	// Formats output date when this DTO is passed through JSON
	@JsonFormat(pattern = "dd/MM/yyyy")
	// Allows dd/MM/yyyy date to be passed into GET request in JSON
	@Temporal(TemporalType.DATE)
	@CreatedDate
	@Column(updatable = false)
	private Date created;

	@OneToMany(mappedBy = "customer")
	private List<Token> token;

	public Customer() {
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Token> getToken() {
		return token;
	}

	public void setToken(List<Token> token) {
		this.token = token;
	}

}
