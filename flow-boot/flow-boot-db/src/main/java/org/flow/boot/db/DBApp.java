package org.flow.boot.db;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

import org.flow.boot.common.util.GsonUtil;

//@EnableJms
//@SpringBootApplication
public class DBApp {
	
	public 
	
	public static void main(String[] args) throws InterruptedException {
		while (true) {
			new Thread() {
				@Override
				public void run() {
					RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
					System.out.println(GsonUtil.toJson(runtimeMXBean));
					System.out.println(Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue());

					System.out.println(Thread.currentThread().getPriority());
				}
			}.start();
			TimeUnit.SECONDS.sleep(100000);
		}
		// SpringApplication.run(DBApp.class, args);

	}
}
