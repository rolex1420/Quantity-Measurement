package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {
    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
        System.out.println("QuantityMeasurementController initialized with service: " + service.getClass().getSimpleName());
    }

    public void performEqualityComparison(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Performing equality comparison: " + quantity1 + " vs " + quantity2);
        QuantityMeasurementEntity result = service.compareQuantities(quantity1, quantity2);
        displayResult(result);
    }

    public void performConversion(QuantityDTO quantity, String targetUnit) {
        System.out.println("Performing conversion: " + quantity + " to " + targetUnit);
        QuantityMeasurementEntity result = service.convertQuantity(quantity, targetUnit);
        displayResult(result);
    }

    public void performAddition(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Performing addition: " + quantity1 + " + " + quantity2);
        QuantityMeasurementEntity result = service.addQuantities(quantity1, quantity2);
        displayResult(result);
    }

    public void performSubtraction(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Performing subtraction: " + quantity1 + " - " + quantity2);
        QuantityMeasurementEntity result = service.subtractQuantities(quantity1, quantity2);
        displayResult(result);
    }

    public void performDivision(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Performing division: " + quantity1 + " ÷ " + quantity2);
        QuantityMeasurementEntity result = service.divideQuantities(quantity1, quantity2);
        displayResult(result);
    }

    public void displayMeasurementHistory() {
        System.out.println("Displaying measurement history");
        System.out.println("\n--- Measurement History ---");
        System.out.println("Measurement history display requested");
        System.out.println("--- End of History ---\n");
    }

    private void displayResult(QuantityMeasurementEntity entity) {
        if (entity.hasError()) {
            System.err.println("Operation failed: " + entity.getErrorMessage());
            System.out.println("ERROR: " + entity.getErrorMessage());
        } else {
            System.out.println("Operation succeeded: " + entity);
            System.out.println("SUCCESS: " + entity);
        }
    }
}
