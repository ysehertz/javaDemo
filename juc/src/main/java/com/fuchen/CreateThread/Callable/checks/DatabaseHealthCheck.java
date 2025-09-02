package com.fuchen.CreateThread.Callable.checks;

import com.fuchen.CreateThread.Callable.HealthCheckResult;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


/**
 * A Callable task that simulates checking a database connection.
 * This check demonstrates failure by THROWING AN EXCEPTION.
 */
public class DatabaseHealthCheck implements Callable<HealthCheckResult> {
    private final String dbUrl;

    public DatabaseHealthCheck(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Override
    public HealthCheckResult call() throws Exception {
        System.out.println("-> Executing Database Check for: " + dbUrl);
        long startTime = System.currentTimeMillis();

        try {
            // Simulate network latency and operation time
            int sleepTime = new Random().nextInt(2000) + 500; // 500ms ~ 2500ms
            TimeUnit.MILLISECONDS.sleep(sleepTime);

            // Simulate a 50/50 chance of connection success or failure
            if (new Random().nextBoolean()) {
                long duration = System.currentTimeMillis() - startTime;
                return new HealthCheckResult(
                    "Database Check (" + dbUrl + ")",
                    true,
                    "Connection successful.",
                    duration
                );
            } else {
                // To indicate failure, we throw a checked exception.
                // The main thread will catch this inside an ExecutionException.
                throw new java.sql.SQLException("Connection timed out to " + dbUrl);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            long duration = System.currentTimeMillis() - startTime;
            // Return a failure result if the task was interrupted
             return new HealthCheckResult(
                    "Database Check (" + dbUrl + ")",
                    false,
                    "Task was interrupted.",
                    duration
                );
        }
    }
}
