package com.edwin.smartdevelop;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 消费类入口
 * @author Edwin
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ServletComponentScan(basePackages = "com.edwin.smartdevelop.core.listener")
@EnableSwagger2Doc
public class SmartDevelopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartDevelopApplication.class, args);
	}

}
