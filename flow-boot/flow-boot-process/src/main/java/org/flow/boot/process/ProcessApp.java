package org.flow.boot.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 程序入口
 * 
 * @author songyz
 *
 */
@EnableDiscoveryClient // 服务发现
@SpringBootApplication
public class ProcessApp {

	public static void main(String[] args) {
		SpringApplication.run(ProcessApp.class, args);

	}

}
