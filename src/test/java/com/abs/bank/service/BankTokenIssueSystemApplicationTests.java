package com.abs.bank.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.abs.banking.AbsBankingApplication;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AbsBankingApplication.class)
@WebAppConfiguration
public class BankTokenIssueSystemApplicationTests {

	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected String mapToJson(TokenRequest obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	// POST API test case
	@Test
	public void issueToken() throws Exception {
		String uri = "/abs/bank/tokens";
		Customer customer = new Customer();
		customer.setId(3);
		customer.setMobile("1234");
		customer.setName("Test Post API");
		customer.setType(CustomerType.REGULAR);
		customer.setAddress(new Address());

		//Services services = new Services.ServicesBuilder(servicename, ServicesType.REGULAR).counters(counters).build();

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

	private List<Counter>  createListOfCounters() {
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
