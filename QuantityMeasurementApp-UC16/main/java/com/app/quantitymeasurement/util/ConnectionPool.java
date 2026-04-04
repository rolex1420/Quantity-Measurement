package com.app.quantitymeasurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private static ConnectionPool instance;
    private final BlockingQueue<Connection> availableConnections;
    private final AtomicInteger activeConnections;
    private final AtomicInteger totalConnections;
    private final int maxPoolSize;
    private final int poolTimeout;
    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    private ConnectionPool() {
        this.driver = ApplicationConfig.getDatabaseDriver();
        this.url = ApplicationConfig.getDatabaseUrl();
        this.username = ApplicationConfig.getDatabaseUsername();
        this.password = ApplicationConfig.getDatabasePassword();
        this.maxPoolSize = ApplicationConfig.getMaxPoolSize();
        this.poolTimeout = ApplicationConfig.getPoolTimeout();
        this.availableConnections = new LinkedBlockingQueue<>(maxPoolSize);
        this.activeConnections = new AtomicInteger(0);
        this.totalConnections = new AtomicInteger(0);
        
        initializePool();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void initializePool() {
        try {
            Class.forName(driver);
            int initialSize = Math.min(ApplicationConfig.getInitialPoolSize(), maxPoolSize);
            for (int i = 0; i < initialSize; i++) {
                availableConnections.offer(createConnection());
            }
            System.out.println("Connection pool initialized with " + initialSize + " connections");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found: " + driver, e);
        }
    }

    private Connection createConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            totalConnections.incrementAndGet();
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database connection", e);
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection connection = availableConnections.poll();
            if (connection != null && !connection.isClosed()) {
                activeConnections.incrementAndGet();
                return connection;
            }

            if (totalConnections.get() < maxPoolSize) {
                connection = createConnection();
                activeConnections.incrementAndGet();
                return connection;
            }

            connection = availableConnections.poll(poolTimeout, java.util.concurrent.TimeUnit.MILLISECONDS);
            if (connection != null && !connection.isClosed()) {
                activeConnections.incrementAndGet();
                return connection;
            }

            throw new SQLException("Connection timeout: No available connections after " + poolTimeout + "ms");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for connection", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    availableConnections.offer(connection);
                    activeConnections.decrementAndGet();
                } else {
                    totalConnections.decrementAndGet();
                }
            } catch (SQLException e) {
                System.err.println("Error checking connection status: " + e.getMessage());
                totalConnections.decrementAndGet();
            }
        }
    }

    public void closeAllConnections() {
        Connection connection;
        while ((connection = availableConnections.poll()) != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        
        totalConnections.set(0);
        activeConnections.set(0);
        System.out.println("All database connections closed");
    }

    public PoolStatistics getPoolStatistics() {
        return new PoolStatistics(
            totalConnections.get(),
            activeConnections.get(),
            availableConnections.size(),
            maxPoolSize
        );
    }

    public static class PoolStatistics {
        private final int totalConnections;
        private final int activeConnections;
        private final int availableConnections;
        private final int maxPoolSize;

        public PoolStatistics(int totalConnections, int activeConnections, int availableConnections, int maxPoolSize) {
            this.totalConnections = totalConnections;
            this.activeConnections = activeConnections;
            this.availableConnections = availableConnections;
            this.maxPoolSize = maxPoolSize;
        }

        public int getTotalConnections() { return totalConnections; }
        public int getActiveConnections() { return activeConnections; }
        public int getAvailableConnections() { return availableConnections; }
        public int getMaxPoolSize() { return maxPoolSize; }

        @Override
        public String toString() {
            return String.format("Pool Statistics - Total: %d, Active: %d, Available: %d, Max: %d",
                totalConnections, activeConnections, availableConnections, maxPoolSize);
        }
    }
}
