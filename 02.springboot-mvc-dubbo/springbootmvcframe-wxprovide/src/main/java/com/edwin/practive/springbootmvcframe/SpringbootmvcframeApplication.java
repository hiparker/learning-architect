package com.edwin.practive.springbootmvcframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringbootmvcframeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootmvcframeApplication.class, args);
	}

}
