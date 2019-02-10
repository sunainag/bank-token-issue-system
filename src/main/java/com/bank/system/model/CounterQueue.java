package com.bank.system.model;

import java.util.concurrent.PriorityBlockingQueue;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "counter_queue")
public class CounterQueue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@OneToOne
	@JoinColumn(name = "counter_id")
	private Counter counter;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "counterQueue")
	private PriorityBlockingQueue<Token> tokens;

	public CounterQueue(Counter counter) {
		this.counter = counter;
		tokens = new PriorityBlockingQueue<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Counter getCounter() {
		return counter;
	}

	public void setCounter(Counter counter) {
		this.counter = counter;
	}

	public PriorityBlockingQueue<Token> getTokens() {
		return tokens;
	}

	public void setTokens(PriorityBlockingQueue<Token> tokens) {
		this.tokens = tokens;
	}

}
