package com.bank.system.model;

import java.util.List;

public class Counter {

	private int id;
	private List<ServiceType> listOfServices;
	private List<Long> tokenIds;
	private CounterType counterType;
	private CounterStatus status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<ServiceType> getListOfServices() {
		return listOfServices;
	}
	public void setListOfServices(List<ServiceType> listOfServices) {
		this.listOfServices = listOfServices;
	}
	public List<Long> getTokenIds() {
		return tokenIds;
	}
	public void setTokenIds(List<Long> tokenIds) {
		this.tokenIds = tokenIds;
	}
	public CounterType getCounterType() {
		return counterType;
	}
	public void setCounterType(CounterType counterType) {
		this.counterType = counterType;
	}
	public CounterStatus getStatus() {
		return status;
	}
	public void setStatus(CounterStatus status) {
		this.status = status;
	}
	
	
	
}
