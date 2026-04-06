package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(OperationType operation);

    @Query("SELECT qm FROM QuantityMeasurementEntity qm WHERE qm.thisMeasurementType = ?1")
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT qm FROM QuantityMeasurementEntity qm WHERE qm.operation = ?1 AND qm.error = false")
    List<QuantityMeasurementEntity> findByOperationAndIsErrorFalse(OperationType operation);

    @Query("SELECT COUNT(qm) FROM QuantityMeasurementEntity qm WHERE qm.operation = ?1 AND qm.error = false")
    long countByOperationAndIsErrorFalse(OperationType operation);

    @Query("SELECT qm FROM QuantityMeasurementEntity qm WHERE qm.error = true")
    List<QuantityMeasurementEntity> findByIsErrorTrue();
}
