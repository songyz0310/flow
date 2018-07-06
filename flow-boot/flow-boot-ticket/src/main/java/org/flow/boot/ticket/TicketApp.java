package org.flow.boot.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 程序入口
 * 
 * @author songyz
 *
 */
@EnableFeignClients // 远程调用
@EnableCircuitBreaker // 断路器
@EnableDiscoveryClient // 服务发现
@SpringBootApplication
public class TicketApp {

	public static void main(String[] args) {
		
		SpringApplication.run(TicketApp.class, args);

	}

}
