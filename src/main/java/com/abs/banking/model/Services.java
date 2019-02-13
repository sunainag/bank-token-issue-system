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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "service")
public class Services {

	public enum Type {
		PREMIUM, REGULAR
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(unique = true)
	private String name;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Type type;

	@Column(name = "next_service_id")
	private Long nextServiceId;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "service_counter_mapping", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "counter_id", referencedColumnName = "id"))
	private List<Counter> counters;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public Long getNextServiceId() {
		return nextServiceId;
	}

	public List<Counter> getCounters() {
		return counters;
	}

	
}
