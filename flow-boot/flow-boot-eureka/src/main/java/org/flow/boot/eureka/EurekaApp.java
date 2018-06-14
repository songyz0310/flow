package org.flow.boot.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 程序入口
 * 
 * @author songyz
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApp {

	public static void main(String[] args) {

		SpringApplication.run(EurekaApp.class, args);

	}

}
