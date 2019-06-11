package org.flow.boot.common.job;

import java.util.LinkedList;
import java.util.List;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WindowQpsControl {

    /**
     * 接受请求窗口
     */
    private Long[] accessWindow;
    /**
     * 窗口大小
     */
    private int limit;
    /**
     * 指针位置
     */
    private int curPosition;
    /**
     * 时间间隔
     */
    private long period;

    private final Object lock = new Object();

    /**
     * 1秒内最多400次请求
     * 
     * @param limit 限制次数 400
     * @param period 时间间隔 1
     * @param timeUnit 间隔类型 秒
     */
    public WindowQpsControl(int limit, int period, TimeUnit timeUnit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + limit);
        }
        curPosition = 0;
        this.period = timeUnit.toMillis(period);
        this.limit = limit;
        accessWindow = new Long[limit];
        Arrays.fill(accessWindow, 0L);
    }

    public void click() {

        if (isPass()) {
            System.out.println("通过");
        }
        else {
            System.out.println("拦截");
        }
    }

    public boolean isPass() {
        long curTime = System.currentTimeMillis();
        synchronized (lock) {
            if (curTime >= period + accessWindow[curPosition]) {
                accessWindow[curPosition++] = curTime;
                curPosition = curPosition % limit;
                return true;
            }
            else {
                return false;
            }
        }
    }

    public static void main(String[] args) {
        WindowQpsControl win = new WindowQpsControl(40, 1, TimeUnit.MINUTES);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            while (true) {
                String name = Thread.currentThread().getName();
                System.out.println(name);
                win.click();
                Thread.sleep(1000);
            }
        });
        executorService.submit(() -> {
            while (true) {
                win.click();
                Thread.sleep(1500);
            }
        });
        executorService.submit(() -> {
            while (true) {
                win.click();
                Thread.sleep(500);
            }
        });

    }

}
