package com.fuchen.oomTest;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * ClassName: oomTest
 * Package: com.fuchen.oomTest
 * Description:
 *
 * @author fuchen
 * @version 1.0
 * @createTime 2025/10/10
 */
public class oomTest {

    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            2 , 2 , 5,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(1),
            new CustomRejectedPolicy()
    );

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0 ;i < Integer.MAX_VALUE; i ++){
            Thread.sleep(100);

            new Thread(() -> {
                doExample(threadPool);
            }).start();
        }
    }

    public  static void doExample(ThreadPoolExecutor threadPool){
        List<Future<Integer>> futures = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            MyTask myTask = new MyTask(i);
            Future<Integer> future = threadPool.submit(myTask::doIt);

            futures.add(future);
        }
        for (Future<Integer> future : futures) {
            try {
                Integer result = future.get();
                System.out.println("任务结果: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static  class CustomRejectedPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            System.out.println("任务被拒绝: " + r.toString());
            if(r instanceof FutureTask<?> futureTask){
                System.out.println("任务状态" + futureTask);
            }
//            throw new RejectedExecutionException("任务 " + r.toString() +
//                    " 被拒绝，当前线程池状态: " +
//                    "活动线程数 = " + e.getActiveCount() +
//                    ", 任务队列大小 = " + e.getQueue().size() +
//                    ", 最大线程数 = " + e.getMaximumPoolSize());
        }
    }
}

