package com.abs.banking.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	public Counter() {
	}

	private Counter(CounterBuilder builder) {
		this();
		this.number = builder.number;
		this.priority = builder.priority;
		this.queueSize = builder.queueSize;
	}

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

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public Collection<Token> getToken() {
		return tokens;
	}

	public void setToken(Collection<Token> token) {
		this.tokens = token;
	}

	@Override
	public String toString() {
		return "Counter [number=" + number + ", priority=" + priority + ", queueSize=" + queueSize + "]";
	}

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
