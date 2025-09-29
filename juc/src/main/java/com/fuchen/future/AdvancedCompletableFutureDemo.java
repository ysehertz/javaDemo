package com.fuchen.future;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * CompletableFuture 的进阶用法演示
 * @author Gemini
 */
@Slf4j
public class AdvancedCompletableFutureDemo {

    // 创建一个通用的线程池
    private static final ExecutorService executor = Executors.newFixedThreadPool(10, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true); // 使用守护线程，这样主线程退出时JVM可以退出
        return t;
    });

    public static void main(String[] args) {
        log.info("主线程开始...");

        // 场景一：编排多个依赖和并行任务，获取用户仪表盘数据
        runDashboardScenario();

        log.info("\n-------------------- 分割线 --------------------\n");

        // 场景二：等待所有独立的清理任务完成 (allOf)
        runAllOfScenario();

        log.info("\n-------------------- 分割线 --------------------\n");
        
        // 场景三：从多个数据源中获取最快返回的结果 (anyOf)
        runAnyOfScenario();

        log.info("主线程结束。");
        // executor.shutdown(); // 由于使用了守护线程，这里可以不显式关闭
    }
    
    /**
     * 场景一：获取用户仪表盘数据
     * 流程:
     * 1. 获取用户ID
     * 2. (依赖1) 使用用户ID获取用户基本信息
     * 3. (并行) 同时使用用户ID获取用户订单列表 和 用户信用积分
     * 4. (合并) 将用户信息、订单列表、信用积分合并成一个仪表盘对象
     */
    private static void runDashboardScenario() {
        log.info("场景一：开始获取仪表盘数据...");

        CompletableFuture<DashboardData> dashboardFuture = fetchUserId()
                .thenComposeAsync(userId -> {
                    log.info("步骤2: 成功获取用户ID: {}, 开始并行获取用户详情、订单和信用分", userId);
                    // fetchUserDetails 依赖 userId 的结果
                    // fetchOrders 和 fetchCreditRating 也可以并行执行
                    CompletableFuture<String> userDetailsFuture = fetchUserDetails(userId);
                    CompletableFuture<List<String>> ordersFuture = fetchOrders(userId);
                    CompletableFuture<Integer> creditRatingFuture = fetchCreditRating(userId);

                    // 使用 thenCombine 合并两个独立的结果
                    return userDetailsFuture.thenCombine(ordersFuture, (details, orders) -> {
                        // 返回一个临时的聚合对象，或者直接链式调用下一个 thenCombine
                        return new DashboardData(details, orders, 0); // 信用分暂时为0
                    }).thenCombine(creditRatingFuture, (dashboard, rating) -> {
                        // dashboard 是上一步聚合的结果，rating 是信用分的结果
                        dashboard.setCreditRating(rating);
                        return dashboard;
                    });
                }, executor)
                .orTimeout(6, TimeUnit.SECONDS) // 为整个编排流程设置一个6秒的超时
                .exceptionally(ex -> {
                    if (ex.getCause() instanceof TimeoutException) {
                        log.error("获取仪表盘数据超时！返回默认仪表盘数据。");
                    } else {
                        log.error("获取仪表盘数据时发生未知异常: {}", ex.getMessage());
                    }
                    return new DashboardData("默认用户", List.of(), -1); // 降级处理
                });

        log.info("主线程已提交仪表盘数据获取任务，不阻塞，继续执行...");
        
        DashboardData result = dashboardFuture.join(); // 阻塞等待最终结果
        log.info("场景一最终结果 -> 仪表盘数据: {}", result);
    }

    /**
     * 场景二：使用 allOf 等待所有任务完成
     */
    private static void runAllOfScenario() {
        log.info("场景二：开始执行多个独立的清理任务...");
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> slowTask("关闭文件流"), executor);
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> slowTask("清除临时缓存"), executor);
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(() -> slowTask("发送统计日志"), executor);

        CompletableFuture<Void> allFinished = CompletableFuture.allOf(task1, task2, task3);

        allFinished.thenRun(() -> {
            log.info("场景二：所有清理任务都已完成！");
        }).join();
    }
    
     /**
     * 场景三：使用 anyOf 获取最快的结果
     */
    private static void runAnyOfScenario() {
        log.info("场景三：从多个镜像源查询同一个资源，使用最快返回的结果...");
        CompletableFuture<String> source1 = CompletableFuture.supplyAsync(() -> queryResource("源A", 3), executor);
        CompletableFuture<String> source2 = CompletableFuture.supplyAsync(() -> queryResource("源B", 2), executor);
        CompletableFuture<String> source3 = CompletableFuture.supplyAsync(() -> queryResource("源C", 4), executor);
        
        CompletableFuture<Object> fastestResult = CompletableFuture.anyOf(source1, source2, source3);
        
        fastestResult.thenAccept(result -> {
             log.info("场景三：最快的结果来自: {}", result);
        }).join();
    }


    // --- 模拟的异步API调用 ---

    private static CompletableFuture<String> fetchUserId() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("API: 获取用户ID...");
            sleep(1);
            return "user-id-001";
        }, executor);
    }

    private static CompletableFuture<String> fetchUserDetails(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("API: 正在获取用户 '{}' 的详细信息...", userId);
            sleep(2);
            return String.format("{\"userId\": \"%s\", \"name\": \"Gemini User\"}", userId);
        }, executor);
    }

    private static CompletableFuture<List<String>> fetchOrders(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("API: 正在获取用户 '{}' 的订单列表...", userId);
            sleep(3); // 这个操作比较慢
            return List.of("订单A", "订单B", "订单C");
        }, executor);
    }

    private static CompletableFuture<Integer> fetchCreditRating(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("API: 正在获取用户 '{}' 的信用积分...", userId);
            sleep(1);
            return 750;
        }, executor);
    }

    // --- 辅助方法和数据类 ---
    private static void slowTask(String taskName) {
        log.info("任务 '{}' 开始...", taskName);
        sleep(2);
        log.info("任务 '{}' 完成.", taskName);
    }
    
    private static String queryResource(String sourceName, int delaySeconds) {
        log.info("正在从 '{}' 查询资源...", sourceName);
        sleep(delaySeconds);
        log.info("'{}' 返回了结果.", sourceName);
        return String.format("来自[%s]的结果", sourceName);
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    // 用于聚合数据的简单Java Bean (在Java 17+中可以用 record)
    static class DashboardData {
        private String userDetails;
        private List<String> orders;
        private int creditRating;

        public DashboardData(String userDetails, List<String> orders, int creditRating) {
            this.userDetails = userDetails;
            this.orders = orders;
            this.creditRating = creditRating;
        }

        public void setCreditRating(int creditRating) {
            this.creditRating = creditRating;
        }

        @Override
        public String toString() {
            return "DashboardData{" +
                    "userDetails='" + userDetails + '\'' +
                    ", orders=" + orders +
                    ", creditRating=" + creditRating +
                    '}';
        }
    }
}