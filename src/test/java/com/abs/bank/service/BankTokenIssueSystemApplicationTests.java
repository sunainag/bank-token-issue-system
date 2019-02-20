package com.abs.bank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abs.banking.dto.TokenRequest;
import com.abs.banking.model.Address;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Counter.Priority;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Customer.CustomerType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BankTokenIssueSystemApplicationTests extends AbstractTest {

	String uri = "/abs/bank/tokens";

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	// GET API test case
	@Test
	public void getActiveTokens() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Map<Integer, List<Integer>> activeTokensPerCounter = super.mapFromJson(content, Map.class);
		System.out.println(activeTokensPerCounter);
		if(activeTokensPerCounter.get(1) != null)
		assertTrue(activeTokensPerCounter.get(1).get(0).intValue()==1);
	}

	// POST API test case
	//@Test
	public void issueToken() throws Exception {

		Customer customer = new Customer();
		customer.setMobile("1234");
		customer.setName("Test Post API");
		customer.setType(CustomerType.REGULAR);
		customer.setAddress(createAddress());

		// Services services = new Services.ServicesBuilder(servicename,
		// ServicesType.REGULAR).counters(counters).build();

		String servicename = "A";
		List<String> service = new ArrayList<String>();
		service.add(0, servicename);

		TokenRequest tokenReq = new TokenRequest();
		tokenReq.setCustomer(customer);
		tokenReq.setServices(service);

		String inputJson = mapToJson(tokenReq);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "Token number assigned:1");
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
