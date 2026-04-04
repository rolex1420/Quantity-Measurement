package com.app.quantitymeasurement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading and managing application configuration.
 * Supports loading from application.properties and system property overrides.
 */
public class ApplicationConfig {
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "application.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ApplicationConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.err.println("Unable to find " + PROPERTIES_FILE);
                return;
            }
            properties.load(input);
            
            // Override with system properties
            Properties systemProperties = System.getProperties();
            for (String key : systemProperties.stringPropertyNames()) {
                if (key.startsWith("database.") || key.startsWith("repository.") || key.startsWith("logging.")) {
                    properties.setProperty(key, systemProperties.getProperty(key));
                }
            }
        } catch (IOException ex) {
            System.err.println("Error loading " + PROPERTIES_FILE + ": " + ex.getMessage());
        }
    }

    public static String getRepositoryType() {
        return properties.getProperty("repository.type", "CACHE");
    }

    public static String getDatabaseDriver() {
        return properties.getProperty("database.driver", "org.h2.Driver");
    }

    public static String getDatabaseUrl() {
        return properties.getProperty("database.url", "jdbc:h2:mem:quantitymeasurement;DB_CLOSE_DELAY=-1;MODE=MySQL");
    }

    public static String getDatabaseUsername() {
        return properties.getProperty("database.username", "sa");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("database.password", "");
    }

    public static int getInitialPoolSize() {
        return Integer.parseInt(properties.getProperty("database.pool.initial.size", "5"));
    }

    public static int getMaxPoolSize() {
        return Integer.parseInt(properties.getProperty("database.pool.max.size", "20"));
    }

    public static int getPoolTimeout() {
        return Integer.parseInt(properties.getProperty("database.pool.timeout", "30000"));
    }

    public static String getLoggingLevel() {
        return properties.getProperty("logging.level.com.app.quantitymeasurement", "INFO");
    }

    public static void printConfiguration() {
        System.out.println("=== Application Configuration ===");
        System.out.println("Repository Type: " + getRepositoryType());
        System.out.println("Database Driver: " + getDatabaseDriver());
        System.out.println("Database URL: " + getDatabaseUrl());
        System.out.println("Initial Pool Size: " + getInitialPoolSize());
        System.out.println("Max Pool Size: " + getMaxPoolSize());
        System.out.println("Pool Timeout: " + getPoolTimeout() + "ms");
        System.out.println("Logging Level: " + getLoggingLevel());
        System.out.println("================================");
    }
}
