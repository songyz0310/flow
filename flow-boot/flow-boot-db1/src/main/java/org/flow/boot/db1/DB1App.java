package org.flow.boot.db1;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class DB1App {
	public static void main(String[] args) throws InterruptedException {
		while (true) {
			new Thread() {
				@Override
				public void run() {
					RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
					System.out.println(runtimeMXBean.getName());
					System.out.println(Integer.valueOf(runtimeMXBean.getName().split("@")[0]).intValue());

					System.out.println(Thread.currentThread().getPriority());
				}
			}.start();
			TimeUnit.SECONDS.sleep(10000);
		}
		// SpringApplication.run(DBApp.class, args);

	}
}
