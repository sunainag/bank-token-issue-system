package com.abs.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

///@EnableJpaAuditing
@SpringBootApplication
public class AbsBankingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AbsBankingApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AbsBankingApplication.class);
	}

}
