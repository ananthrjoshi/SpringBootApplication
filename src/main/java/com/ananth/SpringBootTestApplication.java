package com.ananth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = { "com.ananth" })
public class SpringBootTestApplication {

	public static void main(String[] args) {
		 SpringApplication.run(SpringBootTestApplication.class, args);
	}

}
