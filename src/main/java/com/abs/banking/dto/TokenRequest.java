package com.abs.banking.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.abs.banking.model.Customer;

public class TokenRequest {

	@NotNull
	private List<String> services;

	private Customer customer;

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
