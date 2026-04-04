package com.app.quantitymeasurement.exception;

public class DatabaseException extends RuntimeException {
    
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public static DatabaseException connectionFailed(String url, Throwable cause) {
        String message = String.format("Failed to connect to database: %s", url);
        return new DatabaseException(message, cause);
    }

    public static DatabaseException queryFailed(String query, Throwable cause) {
        String message = String.format("Failed to execute query: %s", query);
        return new DatabaseException(message, cause);
    }

    public static DatabaseException cleanupFailed(String resource, Throwable cause) {
        String message = String.format("Failed to cleanup database resource: %s", resource);
        return new DatabaseException(message, cause);
    }

    public static DatabaseException transactionFailed(Throwable cause) {
        return new DatabaseException("Database transaction failed", cause);
    }

    public static DatabaseException schemaCreationFailed(Throwable cause) {
        return new DatabaseException("Failed to create database schema", cause);
    }

    public static DatabaseException connectionPoolExhausted() {
        return new DatabaseException("Connection pool exhausted - no available connections");
    }

    public static DatabaseException invalidConfiguration(String configKey, String configValue) {
        String message = String.format("Invalid database configuration: %s = %s", configKey, configValue);
        return new DatabaseException(message);
    }
}
