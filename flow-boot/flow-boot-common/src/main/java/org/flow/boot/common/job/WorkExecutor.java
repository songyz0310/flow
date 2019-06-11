package org.flow.boot.common.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.net.util.Base64;

public class WorkExecutor {
    // private ExecutorService es = Executors.newFixedThreadPool(5);

    // 定义装苹果的篮子类
    public static class Basket {

        // 篮子，能够容纳3个苹果
        BlockingQueue<String> basket = new ArrayBlockingQueue<String>(3);

        // 生产苹果，放入篮子
        public void produce() throws InterruptedException {
            basket.put("An apple");
        }

        // 消费苹果
        public String consume() throws InterruptedException {
            return basket.take();
        }

        // 苹果的个数
        public int getAppleNumber() {
            return basket.size();
        }

    }

    public static void testBasket() throws InterruptedException {
        final Basket basket = new Basket();


        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(() -> {
            try {
                while (true) {
                    System.out.println("生产者准备生产苹果：" + System.currentTimeMillis());
                    basket.produce();
                    System.out.println("生产者生产苹果完毕：" + System.currentTimeMillis());
                    System.out.println("生产完后有苹果：" + basket.getAppleNumber() + "个");
                    Thread.sleep(3000);
                }
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
        service.submit(() -> {
            try {
                while (true) {
                    System.out.println("消费者准备消费苹果：" + System.currentTimeMillis());
                    basket.consume();
                    System.out.println("消费者消费苹果完毕：" + System.currentTimeMillis());
                    System.out.println("消费完后有苹果：" + basket.getAppleNumber() + "个");
                    Thread.sleep(2000);
                }
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
        Thread.sleep(1000_10);
        service.shutdown();

    }
    
    public static void main(String[] args) throws InterruptedException {
        WorkExecutor.testBasket();
    }

}
