package com.abs.banking.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author sunainag
 * 
 *         JPA Entity for Token generated for the customer on request
 *
 */
@Entity
@Table(name = "token")
@EntityListeners(AuditingEntityListener.class)
public class Token {

	public enum StatusCode {
		ACTIVE, CANCELLED, COMPLETED, IN_PROGRESS
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private int number;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "customer_id")
	Customer customer;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "token",fetch = FetchType.EAGER)
	private List<TokenServiceMapping> tokenServices;

	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "current_counter_id")
	private Counter currentCounter;

	@ManyToOne
	@JoinColumn(name = "current_service_id")
	Services currentService;

	@Enumerated(EnumType.STRING)
	@NotNull
	private StatusCode statusCode = StatusCode.ACTIVE;

	@Temporal(TemporalType.DATE)
	@CreatedDate
	@Column(updatable = false)
	private Date created;

	public Token() {
		super();
	}

	public Token(int number, Customer cust) {
		this.number = number;
		this.customer = cust;
	}

	public Token(Token t) {
		this(t.getNumber(), t.getCurrentCounter(), t.getCurrentService(), t.getCustomer(), t.getStatusCode());
	}

	private Token(int number,Counter currentCounter, Services currentService, Customer customer,StatusCode statusCode) {
		this.currentCounter=currentCounter;
		this.number=number;
		this.currentService=currentService;
		this.customer=customer;
		this.statusCode=statusCode;
	}

	/******** Getters ************/
	public int getNumber() {
		return number;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<TokenServiceMapping> getTokenServices() {
		return tokenServices;
	}

	public Counter getCurrentCounter() {
		return currentCounter;
	}

	public Services getCurrentService() {
		return currentService;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public boolean isInactive() {
		return StatusCode.CANCELLED.equals(this.statusCode) || StatusCode.COMPLETED.equals(this.statusCode);
	}

	public Integer getCounterNumber() {
		if (this.getCurrentCounter() != null)
			return this.getCurrentCounter().getNumber();
		else
			return Integer.valueOf(0);
	}

	/****************************/

	/******** Setters ************/
	public void setTokenServices(List<TokenServiceMapping> tokenServices) {
		this.tokenServices = tokenServices;
	}

	public void setCurrentCounter(Counter currentCounter) {
		this.currentCounter = currentCounter;
	}

	public void setCurrentService(Services currentService) {
		this.currentService = currentService;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}
	/****************************/

}
