package com.abs.banking.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "service")
public class Services {

	/**
	 * Here the sequence of defining the servicesType is of utmost significance; as
	 * the ordinal of the enum values is being used to compare the priority of the
	 * token @see class TokenPriorityComparator.
	 *
	 */
	public enum ServicesType {
		REGULAR, PREMIUM, URGENT
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(unique = true)
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ServicesType type;

	@Column(name = "next_service_id")
	private Long nextServiceId;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "service_counter_mapping", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "counter_id", referencedColumnName = "id"))
	private List<Counter> counters;

	@OneToMany(mappedBy = "currentService")
	private List<Token> token;

	/********Getters************/
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ServicesType getType() {
		return type;
	}

	public Long getNextServiceId() {
		return nextServiceId;
	}

	public List<Counter> getCounters() {
		return counters;
	}

	public List<Token> getToken() {
		return token;
	}
	/****************************/
	
	public void setType(ServicesType type) {
		this.type = type;
	}

	public void setToken(List<Token> token) {
		this.token = token;
	}

}
