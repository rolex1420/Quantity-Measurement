package com.app.quantitymeasurement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuantityMeasurementEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private final QuantityDTO operand1;
    private final QuantityDTO operand2;
    private final String operation;
    private final QuantityDTO result;
    private final boolean hasError;
    private final String errorMessage;
    private final LocalDateTime timestamp;

    // Constructor for single operand operations (conversion)
    public QuantityMeasurementEntity(QuantityDTO operand1, String operation, QuantityDTO result) {
        this.operand1 = operand1;
        this.operand2 = null;
        this.operation = operation;
        this.result = result;
        this.hasError = false;
        this.errorMessage = null;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor for binary operand operations (addition, subtraction, division, comparison)
    public QuantityMeasurementEntity(QuantityDTO operand1, QuantityDTO operand2, String operation, QuantityDTO result) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
        this.result = result;
        this.hasError = false;
        this.errorMessage = null;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor for error cases
    public QuantityMeasurementEntity(QuantityDTO operand1, String operation, String errorMessage) {
        this.operand1 = operand1;
        this.operand2 = null;
        this.operation = operation;
        this.result = null;
        this.hasError = true;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor for error cases with two operands
    public QuantityMeasurementEntity(QuantityDTO operand1, QuantityDTO operand2, String operation, String errorMessage) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
        this.result = null;
        this.hasError = true;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    public QuantityDTO getOperand1() {
        return operand1;
    }

    public QuantityDTO getOperand2() {
        return operand2;
    }

    public String getOperation() {
        return operation;
    }

    public QuantityDTO getResult() {
        return result;
    }

    public boolean hasError() {
        return hasError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        if (hasError) {
            return String.format("[%s] ERROR: %s | Operation: %s | Input: %s", 
                timestamp.toString(), errorMessage, operation, operand1);
        } else if (operand2 == null) {
            return String.format("[%s] %s %s -> %s", 
                timestamp.toString(), operation, operand1, result);
        } else {
            return String.format("[%s] %s %s %s = %s", 
                timestamp.toString(), operand1, operation, operand2, result);
        }
    }
}
