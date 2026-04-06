package com.app.quantitymeasurement;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class SimpleIntegrationTest {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Test
    void contextLoads() {
        assertNotNull(repository);
    }

    @Test
    void testRepositoryOperations() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setThisValue(1.0);
        entity.setThisUnit("FEET");
        entity.setThisMeasurementType("LengthUnit");
        entity.setThatValue(12.0);
        entity.setThatUnit("INCHES");
        entity.setThatMeasurementType("LengthUnit");
        entity.setOperation(OperationType.COMPARE);
        entity.setResultString("true");
        entity.setError(false);
        
        QuantityMeasurementEntity saved = repository.save(entity);
        
        assertNotNull(saved.getId());
        assertEquals(1.0, saved.getThisValue());
        assertEquals("FEET", saved.getThisUnit());
        assertEquals("LengthUnit", saved.getThisMeasurementType());
        
        List<QuantityMeasurementEntity> compareOperations = repository.findByOperation(OperationType.COMPARE);
        assertFalse(compareOperations.isEmpty());
        
        List<QuantityMeasurementEntity> lengthMeasurements = repository.findByThisMeasurementType("LengthUnit");
        assertFalse(lengthMeasurements.isEmpty());
        
        long count = repository.countByOperationAndIsErrorFalse(OperationType.COMPARE);
        assertTrue(count > 0);
    }
}
