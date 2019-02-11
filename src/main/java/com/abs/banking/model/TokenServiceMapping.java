package com.abs.banking.model;

import javax.persistence.Entity;
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

    public TokenServiceMapping() {
    }

    public TokenServiceMapping(Token token, Service service) {
        this.service = service;
        this.token = token;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "token_id")
    private Token token;

    private String comments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

    
}
