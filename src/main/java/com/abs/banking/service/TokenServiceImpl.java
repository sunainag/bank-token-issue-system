package com.abs.banking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.abs.banking.exception.BusinessException;
import com.abs.banking.exception.BusinessException.ErrorCode;
import com.abs.banking.model.Customer;
import com.abs.banking.model.Services;
import com.abs.banking.model.Token;
import com.abs.banking.model.Token.StatusCode;
import com.abs.banking.model.TokenServiceMapping;
import com.abs.banking.repository.ServiceRepository;
import com.abs.banking.repository.TokenRepository;
import com.abs.banking.util.sequence.generator.SequenceGenerator;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenRepository tokenRepo;

	@Autowired
	ServiceRepository serviceRepo;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Autowired
	TokenQueueService tokenQueueService;

	@Override
	public Token issueToken(Customer customer, List<String> services) {
		Token token = generateTokenNumber(customer);
		assignTokenServices(token, services);
		tokenQueueService.putInQueue(token);
		return saveOrUpdate(token);
	}

	@Override
	public Token getTokenByNumber(Integer tokenNumber) {
		List<Token> token = tokenRepo.findByNumber(tokenNumber);
		if (!CollectionUtils.isEmpty(token))
			return token.get(0); // expected unique column `number` in table `token`
		else
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
	}

	@Override
	public void comment(Integer tokenNumber, String comments) {
		Token token = getTokenByNumber(tokenNumber);
		token.getTokenServices().stream().filter(tsm -> tsm.getService().getId() == token.getCurrentService().getId())
				.findFirst().get().setComments(comments);
		saveOrUpdate(token);
	}

	@Override
	public Services findServiceById(Long nextServiceId) {
		if (serviceRepo.findById(nextServiceId).isPresent())
			return serviceRepo.findById(nextServiceId).get();
		else
			throw new BusinessException(BusinessException.ErrorCode.SERVICE_NOT_FOUND);
	}

	@Override
	public List<Token> findByStatusCode(StatusCode statusCode) {
		return tokenRepo.findByStatusCode(statusCode);
	}

	@Override
	public boolean isTokenInvalid(Token token) {
		return StatusCode.COMPLETED.equals(token.getStatusCode()) || StatusCode.CANCELLED.equals(token.getStatusCode());
	}

	@Override
	public Token save(Token token) {
		return tokenRepo.save(token);
	}
	
	//TODO
	@Override
	public boolean existsToken(Customer customer, List<String> services) {
		/*Services service = findServiceByName(services.get(0));
		return tokenRepo.findByCustomerAndServices(customer.getId(),service.getId()).isPresent();*/
		return true;
	}

	private Token generateTokenNumber(Customer customer) {
		return new Token(sequenceGenerator.generate(), customer);
	}

	private void assignTokenServices(Token token, List<String> services) {
		List<TokenServiceMapping> tokenServicelist = getTokenServices(token, services);
		token.setTokenServices(tokenServicelist);
		token.setCurrentService(tokenServicelist.get(0).getService());
	}

	private List<TokenServiceMapping> getTokenServices(Token token, List<String> tokenServices) {
		List<TokenServiceMapping> tokenServiceList = new ArrayList<>();
		for (String name : tokenServices) {
			Services service = findServiceByName(name);
			if (service == null) {
				throw new BusinessException(BusinessException.ErrorCode.SERVICE_NOT_FOUND);
			}
			tokenServiceList.add(new TokenServiceMapping(token, service));
			while (service.getNextServiceId() != null) {
				Services nextService = findServiceById(service.getNextServiceId());
				tokenServiceList.add(new TokenServiceMapping(token, nextService));
				service = nextService;
			}
		}
		return tokenServiceList;
	}

	private Services findServiceByName(String name) {
		return serviceRepo.findByName(name);
	}

	private Token saveOrUpdate(Token token) {
		return tokenRepo.save(token);
	}

}
