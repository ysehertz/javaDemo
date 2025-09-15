package com.fuchen;

import ch.qos.logback.core.pattern.FormatInfo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ThreadPooldemo
 * Package: com.fuchen
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/9/14
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                10,
                1000l,
                TimeUnit.MICROSECONDS,
                new ArrayBlockingQueue<>(100),
                new MyThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {

            Runnable runnable = new MyRunnable(Integer.toString(i));
            threadPoolExecutor.submit(runnable);
        }

        threadPoolExecutor.shutdown();

        while (!threadPoolExecutor.isTerminated()) {
        }

        System.out.println("Finished all threads");
    }
    public void basicsThreadPool(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,
                10,
                1000l,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(100),
                new MyThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {

            Runnable runnable = new MyRunnable(Integer.toString(i));
            threadPoolExecutor.submit(runnable);
        }

        threadPoolExecutor.shutdown();

        while (!threadPoolExecutor.isTerminated()) {
        }

        System.out.println("Finished all threads");
    }

}
