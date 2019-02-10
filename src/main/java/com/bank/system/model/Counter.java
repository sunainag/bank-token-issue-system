package com.bank.system.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "JoinTableCustomerService")
@Table(name = "counter")
public class Counter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@OneToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name="counter_service_mapping", joinColumns={@JoinColumn(name="counter_id", referencedColumnName="id")}
    , inverseJoinColumns={@JoinColumn(name="service_id", referencedColumnName="id")})
	private List<Service> listOfServices;
	
	@Enumerated(EnumType.STRING)
	@Column
	private ServiceUrgencyLevel serviceLevel;
	
	@Enumerated(EnumType.STRING)
	@Column
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
