package com.abs.banking.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class TokenRequest {
   
	@NotNull
    private String customerMobile;

    @NotNull
    private List<String> services;

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}
    
    
}
