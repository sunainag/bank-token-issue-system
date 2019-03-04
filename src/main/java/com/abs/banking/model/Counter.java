package com.abs.banking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author sunainag
 * 
 * JPA Entity for counter at the ABS bank
 *
 */
@Entity
@Table(name = "counter")
public class Counter {

	public enum Priority {
		HIGH, NORMAL
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private int number;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Column(name = "queue_size")
	private int queueSize;

	@OneToMany(mappedBy = "currentCounter")
	private Collection<Token> tokens = new ArrayList<Token>();

	@ManyToMany(mappedBy = "counters")
	private List<Services> services;
	
	public Counter() {
	}

	private Counter(CounterBuilder builder) {
		this();
		this.number = builder.number;
		this.priority = builder.priority;
		this.queueSize = builder.queueSize;
	}

	/********Getters************/
	public Integer getId() {
		return id;
	}

	public int getNumber() {
		return number;
	}

	public Priority getPriority() {
		return priority;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public Collection<Token> getToken() {
		return tokens;
	}
	
	public List<Services> getServices() {
		return services;
	}
	
	/*******************************/
	
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "Counter [number=" + number + ", priority=" + priority + ", queueSize=" + queueSize + "]";
	}

	/************Builder class*************/
	
	public static class CounterBuilder {

		private int number;
		private Priority priority;
		private int queueSize;

		public CounterBuilder(int number) {
			this.number = number;
		}

		public CounterBuilder priority(Priority priority) {
			this.priority = priority;
			return this;
		}

		public CounterBuilder queueSize(int queueSize) {
			this.queueSize = queueSize;
			return this;
		}

		public Counter build() {
			return new Counter(this);
		}
	}
}
