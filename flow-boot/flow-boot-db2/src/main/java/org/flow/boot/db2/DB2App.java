package org.flow.boot.db2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class DB2App {
	public static void main(String[] args) {

		SpringApplication.run(DB2App.class, args);

	}
}
