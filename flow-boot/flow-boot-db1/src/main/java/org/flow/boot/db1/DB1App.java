package org.flow.boot.db1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class DB1App {
	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(DB1App.class, args);

	}
}
