package com.app.quantitymeasurement;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.util.ApplicationConfig;

public class QuantityMeasurementApp {
    private static QuantityMeasurementController controller;
    private static IQuantityMeasurementRepository repository;

    public static void main(String[] args) {
        System.out.println("=== UC16: Quantity Measurement Application with Database Integration ===");
        
        try {
            ApplicationConfig.printConfiguration();
            initializeApplication();
            demonstrateLengthOperations();
            demonstrateWeightOperations();
            demonstrateVolumeOperations();
            demonstrateTemperatureOperations();
            demonstrateCrossCategoryPrevention();
            displayMeasurementStatistics();
            controller.displayMeasurementHistory();
            closeResources();
            System.out.println("=== UC16 Demonstration Complete ===");
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initializeApplication() {
        System.out.println("Initializing application layers...");
        
        String repositoryType = ApplicationConfig.getRepositoryType();
        System.out.println("Using repository type: " + repositoryType);
        
        switch (repositoryType.toUpperCase()) {
            case "DATABASE":
                repository = new QuantityMeasurementDatabaseRepository();
                System.out.println("Database repository initialized");
                break;
            case "CACHE":
            default:
                repository = QuantityMeasurementCacheRepository.getInstance();
                System.out.println("Cache repository initialized");
                break;
        }
        
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
        System.out.println("Application initialization completed");
    }

    private static void demonstrateLengthOperations() {
        System.out.println("\n--- Length Operations ---");
        
        QuantityDTO length1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO length2 = new QuantityDTO(12.0, "INCHES", "LENGTH");
        
        System.out.println("Equality: " + length1 + " equals " + length2);
        controller.performEqualityComparison(length1, length2);
        
        System.out.println("Conversion: " + length1 + " to INCHES");
        controller.performConversion(length1, "INCHES");
        
        System.out.println("Addition: " + length1 + " + " + length2);
        controller.performAddition(length1, length2);
        
        System.out.println("Subtraction: " + length1 + " - " + length2);
        controller.performSubtraction(length1, length2);
        
        System.out.println("Division: " + length1 + " ÷ " + length2);
        controller.performDivision(length1, length2);
    }

    private static void demonstrateWeightOperations() {
        System.out.println("\n--- Weight Operations ---");
        
        QuantityDTO weight1 = new QuantityDTO(1.0, "KILOGRAM", "WEIGHT");
        QuantityDTO weight2 = new QuantityDTO(1000.0, "GRAM", "WEIGHT");
        
        System.out.println("Equality: " + weight1 + " equals " + weight2);
        controller.performEqualityComparison(weight1, weight2);
        
        System.out.println("Conversion: " + weight1 + " to GRAM");
        controller.performConversion(weight1, "GRAM");
        
        System.out.println("Addition: " + weight1 + " + " + weight2);
        controller.performAddition(weight1, weight2);
        
        System.out.println("Division: " + weight1 + " ÷ " + weight2);
        controller.performDivision(weight1, weight2);
    }

    private static void demonstrateVolumeOperations() {
        System.out.println("\n--- Volume Operations ---");
        
        QuantityDTO volume1 = new QuantityDTO(1.0, "LITRE", "VOLUME");
        QuantityDTO volume2 = new QuantityDTO(1000.0, "MILLILITRE", "VOLUME");
        
        System.out.println("Equality: " + volume1 + " equals " + volume2);
        controller.performEqualityComparison(volume1, volume2);
        
        System.out.println("Conversion: " + volume1 + " to MILLILITRE");
        controller.performConversion(volume1, "MILLILITRE");
        
        System.out.println("Addition: " + volume1 + " + " + volume2);
        controller.performAddition(volume1, volume2);
    }

    private static void demonstrateTemperatureOperations() {
        System.out.println("\n--- Temperature Operations ---");
        
        QuantityDTO temp1 = new QuantityDTO(0.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO temp2 = new QuantityDTO(32.0, "FAHRENHEIT", "TEMPERATURE");
        
        System.out.println("Equality: " + temp1 + " equals " + temp2);
        controller.performEqualityComparison(temp1, temp2);
        
        System.out.println("Conversion: " + temp1 + " to FAHRENHEIT");
        controller.performConversion(temp1, "FAHRENHEIT");
        
        System.out.println("Addition attempt: " + temp1 + " + " + temp2);
        controller.performAddition(temp1, temp2);
        
        System.out.println("Division attempt: " + temp1 + " ÷ " + temp2);
        controller.performDivision(temp1, temp2);
    }

    private static void demonstrateCrossCategoryPrevention() {
        System.out.println("\n--- Cross-Category Prevention ---");
        
        QuantityDTO length = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO weight = new QuantityDTO(1.0, "KILOGRAM", "WEIGHT");
        QuantityDTO volume = new QuantityDTO(1.0, "LITRE", "VOLUME");
        QuantityDTO temperature = new QuantityDTO(0.0, "CELSIUS", "TEMPERATURE");
        
        System.out.println("Length vs Weight comparison:");
        controller.performEqualityComparison(length, weight);
        
        System.out.println("Length vs Temperature comparison:");
        controller.performEqualityComparison(length, temperature);
        
        System.out.println("Weight vs Volume comparison:");
        controller.performEqualityComparison(weight, volume);
    }

    private static void displayMeasurementStatistics() {
        System.out.println("\n--- Measurement Statistics ---");
        
        try {
            int totalCount = repository.getTotalCount();
            System.out.println("Total measurements stored: " + totalCount);
            
            System.out.println("Repository statistics: " + repository.getPoolStatistics());
            
            System.out.println("\nMeasurements by operation:");
            System.out.println("COMPARE: " + repository.getMeasurementsByOperation("COMPARE").size());
            System.out.println("CONVERT: " + repository.getMeasurementsByOperation("CONVERT").size());
            System.out.println("ADD: " + repository.getMeasurementsByOperation("ADD").size());
            System.out.println("SUBTRACT: " + repository.getMeasurementsByOperation("SUBTRACT").size());
            System.out.println("DIVIDE: " + repository.getMeasurementsByOperation("DIVIDE").size());
            
            System.out.println("\nMeasurements by type:");
            System.out.println("LENGTH: " + repository.getMeasurementsByType("LENGTH").size());
            System.out.println("WEIGHT: " + repository.getMeasurementsByType("WEIGHT").size());
            System.out.println("VOLUME: " + repository.getMeasurementsByType("VOLUME").size());
            System.out.println("TEMPERATURE: " + repository.getMeasurementsByType("TEMPERATURE").size());
            
        } catch (Exception e) {
            System.err.println("Error displaying statistics: " + e.getMessage());
        }
    }

    private static void closeResources() {
        System.out.println("Closing application resources");
        try {
            if (repository != null) {
                repository.releaseResources();
                System.out.println("Repository resources closed successfully");
            }
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    public static void deleteAllMeasurements() {
        System.out.println("Deleting all measurements");
        try {
            if (repository != null) {
                repository.clearAll();
                System.out.println("All measurements deleted successfully");
            }
        } catch (Exception e) {
            System.err.println("Error deleting measurements: " + e.getMessage());
        }
    }
}
