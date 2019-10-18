package com.edwin.practive.springbootmvcframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SpringbootmvcframeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootmvcframeApplication.class, args);
	}

}
