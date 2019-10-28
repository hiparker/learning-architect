package com.edwin.practive.springbootmvcframe;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan(basePackages = "com.edwin.practive.springbootmvcframe.core.listener")
@EnableSwagger2Doc
public class SpringbootmvcframeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootmvcframeApplication.class, args);
	}

}
