package org.flow.boot.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 程序入口
 * 
 * @author songyz
 *
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class TicketApp {

	public static void main(String[] args) {
		
		SpringApplication.run(TicketApp.class, args);

	}

}
