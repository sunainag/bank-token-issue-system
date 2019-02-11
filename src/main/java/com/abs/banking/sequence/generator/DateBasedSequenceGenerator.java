package com.abs.banking.sequence.generator;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DateBasedSequenceGenerator implements SequenceGenerator {

	private AtomicInteger sequence = new AtomicInteger(0);

	@Override
	public int generate() {
		return sequence.incrementAndGet();
	}

	/*
	 * Reset the counter at the beginning of each day
	 */
	@Scheduled(cron = "0 0 * * *")
	public void reset() {
		sequence.set(0);
	}
}
