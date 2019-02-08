package com.bank.system.model;

public class Token {

	private long tokenNumber;
	private ServiceType tokenType;
	private TokenStatus status;
	private int counterId;
	private Customer customer;
	
	public long getTokenNumber() {
		return tokenNumber;
	}
	public void setTokenNumber(long tokenNumber) {
		this.tokenNumber = tokenNumber;
	}
	public ServiceType getTokenType() {
		return tokenType;
	}
	public void setTokenType(ServiceType tokenType) {
		this.tokenType = tokenType;
	}
	public TokenStatus getStatus() {
		return status;
	}
	public void setStatus(TokenStatus status) {
		this.status = status;
	}
	public int getCounterId() {
		return counterId;
	}
	public void setCounterId(int counterId) {
		this.counterId = counterId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
