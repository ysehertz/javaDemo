package com.fuchen.CreateThread.Callable.checks;

import com.fuchen.CreateThread.Callable.HealthCheckResult;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


/**
 * A Callable task that simulates checking local disk space.
 * This task is designed to always succeed quickly.
 */
public class DiskSpaceHealthCheck implements Callable<HealthCheckResult> {
    private final String diskPath;

    public DiskSpaceHealthCheck(String diskPath) {
        this.diskPath = diskPath;
    }

    @Override
    public HealthCheckResult call() {
        System.out.println("-> Executing Disk Space Check for: " + diskPath);
        long startTime = System.currentTimeMillis();
        String checkIdentifier = "Disk Space Check (" + diskPath + ")";

        try {
            // Simulate a fast I/O operation
            TimeUnit.MILLISECONDS.sleep(200);

            long freeSpaceGB = new Random().nextInt(500) + 50;
            long duration = System.currentTimeMillis() - startTime;

            // This task always returns a success result
            return new HealthCheckResult(checkIdentifier, true, "Space available: " + freeSpaceGB + " GB", duration);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            long duration = System.currentTimeMillis() - startTime;
            return new HealthCheckResult(checkIdentifier, false, "Task was interrupted.", duration);
        }
    }
}
