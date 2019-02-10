package com.bank.system.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tokenNumber")
	private long tokenNumber;
	
	private List<Service> servicesRequired;
	
	@Enumerated(EnumType.STRING)
	@Column
	private TokenStatus status;
	
	@OneToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@Enumerated(EnumType.STRING)
	@Column
	private ServiceUrgencyLevel serviceLevel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "counter_queue_id", nullable = false)
	private CounterQueue counterQueue;
	
	@Column
	private String comments;
	
	public long getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(long tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public List<Service> getServicesRequired() {
		return servicesRequired;
	}

	public void setServicesRequired(List<Service> servicesRequired) {
		this.servicesRequired = servicesRequired;
	}

	public TokenStatus getStatus() {
		return status;
	}

	public void setStatus(TokenStatus status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ServiceUrgencyLevel getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(ServiceUrgencyLevel serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public CounterQueue getCounterQueue() {
		return counterQueue;
	}

	public void setCounterQueue(CounterQueue counterQueue) {
		this.counterQueue = counterQueue;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
