package com.bank.system.model;

import java.util.List;

public class Counter {

	private int id;
	private List<Service> listOfServices;
	private ServiceUrgencyLevel serviceLevel;
	private CounterStatus status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Service> getListOfServices() {
		return listOfServices;
	}

	public void setListOfServices(List<Service> listOfServices) {
		this.listOfServices = listOfServices;
	}

	public ServiceUrgencyLevel getCounterType() {
		return serviceLevel;
	}

	public void setCounterType(ServiceUrgencyLevel counterType) {
		this.serviceLevel = counterType;
	}

	public CounterStatus getStatus() {
		return status;
	}

	public void setStatus(CounterStatus status) {
		this.status = status;
	}

}
