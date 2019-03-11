package com.abs.bank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Address;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Counter.Priority;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Customer.CustomerType;
import com.abs.banking.model.Token;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankTokenIssueSystemApplicationTests extends AbstractTest {

	private static final String uri = "/abs/bank";
	private static final String tokenUri = "/tokens";
	private static final String counterUri = "/counters";

	private int counterNumber;
	private int tokenNumber;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		counterNumber = getCounterNumber();
		tokenNumber = 0;
	}

	// GET API test case for all tokens
	@Test
	public void testA_getActiveTokens() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(uri + tokenUri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	// exception handler test case
	@Test // (expected = InvalidTokenException.class)
	public void testB_whenInvalidTokenNumber_thenException() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(uri + tokenUri + "/5").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
	}

	// PUT API test case
	@Test
	public void testC_issueToken() throws Exception {

		TokenRequest[] requests = new TokenRequest[3];

		requests[0] = createTokenRequest("A", "Person A", "1234", CustomerType.PREMIUM);
		// requests[1] = createTokenRequest("B", "Person B", "5678",
		// CustomerType.REGULAR);
		// requests[2] = createTokenRequest("C", "Person C", "91011",
		// CustomerType.REGULAR);

		for (TokenRequest tokenRequest : requests) {
			if (tokenRequest != null) {
				String inputJson = mapToJson(tokenRequest);
				MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + tokenUri)
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

				int status = mvcResult.getResponse().getStatus();
				assertEquals(201, status);
				String content = mvcResult.getResponse().getContentAsString();
				assertNotNull(content);
				tokenNumber = Integer
						.parseInt(content.substring(content.indexOf(':') + 1, content.indexOf(':') + 2).trim());
			}
		}
	}

	// GET API test case for given tokenNumber
	@Test
	public void testD_getToken() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.get(uri + tokenUri + "/" + 1).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	// GET API test case for next token number from queue for a counter
	@Test
	public void testE_getNextTokenInQueue() throws Exception {
		int counterNumber = this.counterNumber;
		String getTokenUri = "/" + counterNumber + "/token";
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get(uri + counterUri + getTokenUri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		System.out.println(status);
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Token tokenServed = super.mapFromJson(content, Token.class);
		if (tokenServed != null) {
			assertEquals(tokenServed.getCounterNumber().toString(), String.valueOf(counterNumber));
			this.tokenNumber = tokenServed.getNumber();
		}
	}

	// PATCH API test case
	@Test
	public void testF_comment() throws Exception {

		int counterNumber = this.counterNumber;
		int tokenNumber = this.tokenNumber != 0 ? this.tokenNumber : 1;
		String patchUri = "/" + counterNumber + "/tokens/" + tokenNumber;

		Map<String, Object> updates = new HashMap<>();
		updates.put("comments", "Bring papers");

		String inputJson = super.mapToJson(updates);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.patch(uri + counterUri + patchUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "comments updated");

	}
	
	private TokenRequest createTokenRequest(String servicename, String custName, String custMobile, CustomerType type) {
		List<String> service = new ArrayList<String>();
		service.add(0, servicename);

		TokenRequest tokenReq = new TokenRequest();
		tokenReq.setCustomer(createCustomer(custMobile, custName, type));
		tokenReq.setServices(service);
		return tokenReq;
	}

	private Customer createCustomer(String mobile, String name, CustomerType type) {
		Customer customer = new Customer();
		customer.setMobile(mobile);
		customer.setName(name);
		customer.setType(type);
		customer.setAddress(createAddress());
		return customer;
	}

	private Address createAddress() {
		Address address = new Address();
		address.setAddressLine1("one");
		address.setAddressLine2("two");
		address.setCity("city");
		address.setState("state");
		address.setCountry("country");
		address.setZipCode("1232");
		return address;
	}

	private int getCounterNumber() {
		return 3;
	}

	private List<Counter> createListOfCounters() {
		Counter c1 = new Counter.CounterBuilder(1).priority(Priority.NORMAL).queueSize(0).build();
		Counter c2 = new Counter.CounterBuilder(2).priority(Priority.NORMAL).queueSize(0).build();
		Counter c3 = new Counter.CounterBuilder(3).priority(Priority.HIGH).queueSize(0).build();
		Counter c4 = new Counter.CounterBuilder(4).priority(Priority.NORMAL).queueSize(0).build();

		List<Counter> counters = new ArrayList<>();
		counters.add(c1);
		counters.add(c2);
		counters.add(c3);
		counters.add(c4);

		return counters;
	}

}
