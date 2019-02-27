package com.abs.banking.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "service_counter_mapping")
public class ServiceCounterMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "service_id")
	private Services service;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "counter_id")
	private Counter counter;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Customer.CustomerType type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}

	public Counter getCounter() {
		return counter;
	}

	public void setCounter(Counter counter) {
		this.counter = counter;
	}

	public Customer.CustomerType getType() {
		return type;
	}

	public void setType(Customer.CustomerType type) {
		this.type = type;
	}

}