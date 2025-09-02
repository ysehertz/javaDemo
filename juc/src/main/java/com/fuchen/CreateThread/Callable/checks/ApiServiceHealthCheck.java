package com.fuchen.CreateThread.Callable.checks;

import com.fuchen.CreateThread.Callable.HealthCheckResult;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
/**
 * A Callable task that simulates checking an external API service.
 * This check demonstrates failure by RETURNING A FAILED RESULT OBJECT, not by exception.
 */
public class ApiServiceHealthCheck implements Callable<HealthCheckResult> {
    private final String serviceName;
    private final String apiUrl;

    public ApiServiceHealthCheck(String serviceName, String apiUrl) {
        this.serviceName = serviceName;
        this.apiUrl = apiUrl;
    }

    @Override
    public HealthCheckResult call() {
        System.out.println("-> Executing API Check for: " + serviceName);
        long startTime = System.currentTimeMillis();
        String checkIdentifier = "API Check (" + serviceName + ")";

        try {
            // Simulate network request time
            int sleepTime = new Random().nextInt(1500) + 300; // 300ms ~ 1800ms
            TimeUnit.MILLISECONDS.sleep(sleepTime);

            // Simulate an 80% success rate
            if (new Random().nextDouble() > 0.2) {
                 long duration = System.currentTimeMillis() - startTime;
                 return new HealthCheckResult(checkIdentifier, true, "Service is available (HTTP 200 OK)", duration);
            } else {
                 long duration = System.currentTimeMillis() - startTime;
                 // The task itself completed without error, but the business logic indicates failure.
                 return new HealthCheckResult(checkIdentifier, false, "Service Unavailable (HTTP 503)", duration);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            long duration = System.currentTimeMillis() - startTime;
            return new HealthCheckResult(checkIdentifier, false, "Task was interrupted.", duration);
        }
    }
}
