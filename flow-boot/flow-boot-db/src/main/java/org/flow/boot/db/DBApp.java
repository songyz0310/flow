package org.flow.boot.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms	
@SpringBootApplication
public class DBApp {
	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(DBApp.class, args);

	}
}
