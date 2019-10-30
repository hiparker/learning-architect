package com.edwin.smartdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 生产类入口
 * @author Edwin
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SmartDevelopApplication{

	public static void main(String[] args) {
		SpringApplication.run(SmartDevelopApplication.class, args);
	}

}
