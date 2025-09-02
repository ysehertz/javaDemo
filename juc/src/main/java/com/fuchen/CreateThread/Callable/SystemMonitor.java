package com.fuchen.CreateThread.Callable;

import com.fuchen.CreateThread.Callable.checks.ApiServiceHealthCheck;
import com.fuchen.CreateThread.Callable.checks.DatabaseHealthCheck;
import com.fuchen.CreateThread.Callable.checks.DiskSpaceHealthCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * The main application class that orchestrates the concurrent health checks.
 */
public class SystemMonitor {

    public static void main(String[] args) {
        System.out.println("System Monitor starting concurrent health checks...");
        long totalStartTime = System.currentTimeMillis();

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 创建任务列表
        List<Callable<HealthCheckResult>> tasks = new ArrayList<>();
        tasks.add(new DatabaseHealthCheck("jdbc:mysql://primary-db:3306/users"));
        tasks.add(new DatabaseHealthCheck("jdbc:postgresql://reporting-db:5432/sales"));
        tasks.add(new ApiServiceHealthCheck("Authentication Service", "https://api.example.com/auth/health"));
        tasks.add(new ApiServiceHealthCheck("Payment Gateway", "https://api.payment.com/status"));
        tasks.add(new DiskSpaceHealthCheck("/var/logs"));

        try {

            // 提交任务并获取Future列表
            // `invokeAll`方法是一个阻塞方法，它会等待所有任务完成。
            List<Future<HealthCheckResult>> futures = executor.invokeAll(tasks);

            System.out.println("\nAll health checks have completed. Collecting results...\n");

            List<HealthCheckResult> results = new ArrayList<>();
            // 使用迭代器遍历Future列表
            for (Future<HealthCheckResult> future : futures) {
                try {
                    // `future.get()`会获取Callable的call()方法返回的结果。
                    // 如果Callable的call()方法抛出了异常，`future.get()`会重新抛出该异常，
                    HealthCheckResult result = future.get();
                    results.add(result);
                } catch (InterruptedException e) {
                    // 如果主线程被中断，等待结果时会抛出此异常。
                    Thread.currentThread().interrupt();
                    System.err.println("Main thread was interrupted while waiting for a result.");
                } catch (ExecutionException e) {
                    // 处理Callable抛出的异常。
                    Throwable cause = e.getCause(); // Get the original exception (e.g., SQLException)
                    System.err.println("A health check task failed with an exception: " + cause.getMessage());
                    // We can create a custom failure result to add to our report.
                    results.add(new HealthCheckResult(
                        "A FAILED TASK", false, "Task failed due to: " + cause.getMessage(), 0
                    ));
                }
            }

            // 将结果添加到报告
            printReport(results, totalStartTime);

        } catch (InterruptedException e) {
            // This is thrown if the thread is interrupted while `invokeAll` is waiting.
            Thread.currentThread().interrupt();
            System.err.println("The health check execution was interrupted.");
        } finally {
            // 7. Always shut down the executor service to release resources.
            executor.shutdown();
            System.out.println("\nExecutor service has been shut down.");
        }
    }

    private static void printReport(List<HealthCheckResult> results, long totalStartTime) {
        System.out.println("\n========================================");
        System.out.println("   System Health Check Final Report");
        System.out.println("========================================");

        boolean overallSuccess = true;
        for (HealthCheckResult result : results) {
            System.out.println(result);
            if (!result.isSuccess()) {
                overallSuccess = false;
            }
        }

        long totalDuration = System.currentTimeMillis() - totalStartTime;
        System.out.println("----------------------------------------");
        System.out.println("Generated At: " + new java.util.Date());
        System.out.println("Total Execution Time: " + totalDuration + " ms");
        System.out.println("Overall System Status: " + (overallSuccess ? "✅ HEALTHY" : "❌ UNHEALTHY - ACTION REQUIRED"));
        System.out.println("========================================");
    }
}
