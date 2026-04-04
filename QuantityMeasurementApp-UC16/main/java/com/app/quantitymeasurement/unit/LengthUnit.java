package com.app.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable, SupportsArithmetic {
    FEET(1, () -> true),
    INCHES(1.0 / 12, () -> true),
    YARDS(3, () -> true),
    CM(0.0328084, () -> true),
    CENTIMETERS(0.0328084, () -> true);

    final double factor;
    private final SupportsArithmetic supportsArithmetic;

    LengthUnit(double f, SupportsArithmetic supportsArithmetic) {
        factor = f;
        this.supportsArithmetic = supportsArithmetic;
    }

    @Override
    public boolean isSupported() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        // Length units support all arithmetic operations
    }

    double getFactor() {
        return factor;
    }   

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) throw new IllegalArgumentException();
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}
