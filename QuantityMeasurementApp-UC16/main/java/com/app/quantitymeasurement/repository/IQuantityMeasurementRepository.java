package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {
    void save(QuantityMeasurementEntity entity);
    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);
    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);
    int getTotalCount();
    void clearAll();
    
    // Default methods for enhanced functionality
    default String getPoolStatistics() {
        return "Pool statistics not available for this repository implementation";
    }
    
    default void releaseResources() {
        // Default implementation - does nothing
        // Override in implementations that need resource cleanup
    }
}
