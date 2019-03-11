package com.abs.banking.model;

import javax.persistence.Column;
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
@Table(name = "token_service_mapping")
public class TokenServiceMapping {
	
	public enum ServicePriority {
		REGULAR, PREMIUM, URGENT
	}

	public TokenServiceMapping() {
	}

	public TokenServiceMapping(Token token, Services service) {
		this.service = service;
		this.token = token;
		this.priority=ServicePriority.valueOf(service.getType().name());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "service_id")
	private Services service;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "token_id")
	private Token token;

	@Column
	private String comments;
	
	@Enumerated(EnumType.STRING)
	@Column
	private ServicePriority priority;

	/**********Getters******************/
	public long getId() {
		return id;
	}

	public Services getService() {
		return service;
	}

	public Token getToken() {
		return token;
	}

	public String getComments() {
		return comments;
	}
	
	public ServicePriority getPriority() {
		return priority;
	}

	/****************************/

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public void setPriority(ServicePriority priority) {
		this.priority = priority;
	}

}
