package com.abs.banking.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abs.banking.counter.allocator.CounterAllocator;
import com.abs.banking.dto.TokenRequest;
import com.abs.banking.exception.BusinessException;
import com.abs.banking.exception.BusinessException.ErrorCode;
import com.abs.banking.model.Counter;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Service;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;
import com.abs.banking.model.TokenServiceMapping;
import com.abs.banking.sequence.generator.SequenceGenerator;
import com.abs.banking.service.CounterService;
import com.abs.banking.service.CustomerService;
import com.abs.banking.service.TokenService;

@Component
public class CounterManagerImpl implements CounterManager {

	@Autowired
	CustomerService customerService;

	@Autowired
	TokenService tokenService;

	@Autowired
	CounterService counterService;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Autowired
	CounterAllocator counterAllocator;

	@Override
	public Long createToken(TokenRequest tokenReq) {
		if (tokenReq.getCustomer() == null)
			throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
		Customer customer = customerService.findByMobile(tokenReq.getCustomer().getMobile());
		if (customer == null) {
			customerService.createCustomer(tokenReq.getCustomer());
		}
		Token token = new Token();
		token.setNumber(sequenceGenerator.generate());

		List<TokenServiceMapping> tokenServices = new ArrayList<>();
		for (String svc : tokenReq.getServices()) {
			Service service = tokenService.findServiceByName(svc);
			if (service == null) {
				throw new BusinessException(BusinessException.ErrorCode.SERVICE_NOT_FOUND);
			}
			tokenServices.add(new TokenServiceMapping(token, service));
			while (service.getNextServiceId() != null) {
				Service nextService = tokenService.findServiceById(service.getNextServiceId());
				tokenServices.add(new TokenServiceMapping(token, nextService));
				service = nextService;
			}
		}

		token.setTokenServices(tokenServices);
		token.setCreated(new Date());
		token.setStatusCode(Token.StatusCode.ACTIVE);
		token.setCustomer(customer);
		token.setCurrentService(tokenServices.get(0).getService());
		Counter counter = counterAllocator.allocate(tokenServices.get(0).getService(), customer);
		counterService.incrementQueueSize(counter.getId());
		token.setCurrentCounter(counter);
		token = tokenService.saveOrUpdateToken(token);
		return token.getNumber();
	}

	@Override
	public Token getToken(Long tokenId) {
		return tokenService.getTokenById(tokenId);
	}

	@Override
	public void setComments(@NotNull Long tokenNumber, String comments) {
		Token token = getToken(tokenNumber);
		token.getTokenServices().stream().filter(tsm -> tsm.getService().getId() == token.getCurrentService().getId())
				.findFirst().get().setComments(comments);
		tokenService.saveOrUpdateToken(token);
	}

	@Override
	public void updateTokenStatusById(Long tokenNumber, StatusCode tokenStatus) {
		Token token = getToken(tokenNumber);
		Counter currentCounter = token.getCurrentCounter();
		counterService.decrementQueueSize(currentCounter.getId());

		if (StatusCode.COMPLETED.equals(tokenStatus)) {
			TokenServiceMapping nextService = null;
			Iterator<TokenServiceMapping> i = token.getTokenServices().iterator();
			while (i.hasNext()) {
				TokenServiceMapping tsm = i.next();
				if (tsm.getService().getId() == token.getCurrentService().getId() && i.hasNext()) {
					nextService = i.next();
					break;
				}
			}

			if (nextService != null) {
				token.setCurrentService(nextService.getService());
				Counter nextCounter = counterAllocator.allocate(nextService.getService(), token.getCustomer());
				currentCounter.setQueueSize(nextCounter.getQueueSize() + 1);
				token.setCurrentCounter(nextCounter);
			}
			else {
				token.setStatusCode(Token.StatusCode.COMPLETED);
			}

		}
		else if (StatusCode.CANCELLED.equals(tokenStatus)) {
			token.setStatusCode(tokenStatus);
		}

		tokenService.saveOrUpdateToken(token);
	}

	@Override
	public List<Counter> getAllCounters() {
		return counterService.getAll();
	}

	@Override
	public Map<Object, List<Long>> getActiveTokens() {
		List<Token> activeTokens = tokenService.findByStatusCode(Token.StatusCode.ACTIVE);
		Map<Object, List<Long>> counterToTokens = activeTokens.stream().collect(Collectors.groupingBy(
				t -> t.getCurrentCounter().getNumber(), Collectors.mapping(Token::getNumber, Collectors.toList())));
		return counterToTokens;
	}

}
