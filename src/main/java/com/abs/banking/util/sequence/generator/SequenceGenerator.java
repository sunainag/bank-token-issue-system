package com.abs.banking.util.sequence.generator;

/**
 * @author sunainag
 * 
 * Generate sequence based on different algorithms
 * used for token number assigned to the customer (@see Token)
 *
 */
public interface SequenceGenerator {

	/**
	 * Token generation logic
	 * @return
	 */
	int generate();
}
