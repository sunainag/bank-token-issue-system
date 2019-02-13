package com.abs.banking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
	
	@Override
	public Token generateToken(Customer customer, List<String> tokenServices) {
		Token token = new Token(sequenceGenerator.generate(), customer);

		List<TokenServiceMapping> tokenServicelist = getTokenServices(token,tokenServices);

		token.setTokenServices(tokenServicelist);
		token.setCreated(new Date());
		token.setCurrentService(tokenServicelist.get(0).getService());
		return token;
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

	@Override
	public Token getTokenByNumber(Integer tokenNumber) {
		List<Token> token = tokenRepo.findByNumber(tokenNumber);
		if (!CollectionUtils.isEmpty(token))
			return token.get(0); // expected unique column `number` in table `token`
		else
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
	}

	@Override
	public Token saveOrUpdate(Token token) {
		return tokenRepo.save(token);
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
	public boolean assignNextService(Token token) {
		TokenServiceMapping nextService = null;
		Iterator<TokenServiceMapping> i = token.getTokenServices().iterator();
		while (i.hasNext()) {
			TokenServiceMapping tsm = i.next();
			if (tsm.getService().getId() == token.getCurrentService().getId() && i.hasNext()) {
				nextService = i.next();
				token.setCurrentService(nextService.getService());
				saveOrUpdate(token);
				return true;
			}
		}
		return false;
	}
	
	private Services findServiceByName(String name) {
		return serviceRepo.findByName(name);
	}

	@Override
	public void updateStatus(Token token,StatusCode statusCode) {
		token.setStatusCode(statusCode);
		saveOrUpdate(token);
	}


}
