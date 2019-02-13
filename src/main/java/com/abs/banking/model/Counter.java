package com.abs.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	@Override
	public String toString() {
		return "Counter [number=" + number + ", priority=" + priority + ", queueSize=" + queueSize + "]";
	}
}
