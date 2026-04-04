package com.app.quantitymeasurement.unit;

import java.util.function.DoubleBinaryOperator;

public interface IMeasurable {
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
    
    default boolean supportsArithmetic() {
        return true;
    }
    
    default void validateOperationSupport(String operation) {
        if (!supportsArithmetic()) {
            throw new UnsupportedOperationException(operation + " not supported for this measurement type");
        }
    }
}
