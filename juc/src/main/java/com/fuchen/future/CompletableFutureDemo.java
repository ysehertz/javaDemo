package com.fuchen.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture 的一个简单演示
 * @author Gemini
 */
@Slf4j
public class CompletableFutureDemo {

    public static void main(String[] args) {
        // 为了更好地管理线程资源，建议使用自定义的线程池
        // 如果不指定线程池，CompletableFuture 默认会使用 ForkJoinPool.commonPool()
        ExecutorService executor = Executors.newFixedThreadPool(5);

        log.info("主线程开始运行...");

        // 1. supplyAsync: 启动一个异步任务，并期望它返回一个结果
        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> {
            log.info("任务1：开始执行一个耗时的查询操作... [线程: {}]", Thread.currentThread().getName());
            try {
                // 模拟2秒的耗时操作
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("任务1被中断", e);
            }
            log.info("任务1：查询操作完成。");
            return "User-Profile-123";
        }, executor);

        // 2. thenApply: 当上一个任务完成后，将其结果作为输入，进行转换，并返回一个新的结果
        CompletableFuture<String> transformFuture = supplyFuture.thenApply(userId -> {
            log.info("任务2：接收到用户ID '{}'，开始获取用户详情... [线程: {}]", userId, Thread.currentThread().getName());
            // 模拟转换操作
            return "{\"userId\": \"" + userId + "\", \"name\": \"Gemini\"}";
        });

        // 3. thenAccept: 当上一个任务完成后，消费其结果，但不再返回任何值
        CompletableFuture<Void> processFuture = transformFuture.thenAccept(userInfo -> {
            log.info("任务3：接收到用户信息 '{}'，进行打印处理... [线程: {}]", userInfo, Thread.currentThread().getName());
        });
        
        // 4. exceptionally: 演示异常处理
        CompletableFuture.supplyAsync(() -> {
            log.info("任务4：尝试执行一个可能失败的操作... [线程: {}]", Thread.currentThread().getName());
            if (true) { // 模拟一个必然发生的异常
                throw new IllegalStateException("模拟数据库连接失败");
            }
            return "永远不会返回的结果";
        }, executor).exceptionally(ex -> {
            log.error("任务4：捕获到异常！异常信息: {} [线程: {}]", ex.getMessage(), Thread.currentThread().getName());
            return "默认值"; // 返回一个降级或默认的结果
        }).thenAccept(result -> {
             log.info("任务4：最终处理结果: {}", result);
        });


        // 5. runAsync: 启动一个没有返回值的异步任务
        CompletableFuture<Void> notificationFuture = CompletableFuture.runAsync(() -> {
            log.info("任务5：开始发送一个异步通知... [线程: {}]", Thread.currentThread().getName());
             try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.info("任务5：通知发送完成。");
        }, executor);


        log.info("主线程继续执行其他任务...");

        // 为了在演示中看到所有异步任务的日志输出，我们需要阻塞主线程
        // 在实际应用中，主线程通常不会这样等待，比如在Web服务器中，请求处理线程会直接返回
        processFuture.join(); // 等待任务3完成
        notificationFuture.join(); // 等待任务5完成

        log.info("主线程运行结束，关闭线程池。");
        executor.shutdown();
    }
}