package com.fuchen.CreateThread.Callable;

/**
 * A Data Transfer Object (DTO) to encapsulate the result of a single health check.
 * This object is returned by each Callable task.
 */
public class HealthCheckResult {
    private final String checkName;
    private final boolean isSuccess;
    private final String message;
    private final long durationMillis;

    public HealthCheckResult(String checkName, boolean isSuccess, String message, long durationMillis) {
        this.checkName = checkName;
        this.isSuccess = isSuccess;
        this.message = message;
        this.durationMillis = durationMillis;
    }

    public String getCheckName() {
        return checkName;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    /**
     * Provides a formatted string representation of the check result.
     * @return A user-friendly string.
     */
    @Override
    public String toString() {
        // A helper to format the output string
        String status = isSuccess ? "✅ SUCCESS" : "❌ FAILED ";
        return String.format(
            "%-40s [%s] (%d ms) - %s",
            checkName,
            status,
            durationMillis,
            message
        );
    }
}
