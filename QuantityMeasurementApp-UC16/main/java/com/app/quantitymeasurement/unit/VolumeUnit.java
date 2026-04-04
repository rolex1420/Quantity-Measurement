package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable, SupportsArithmetic {
    LITRE(1.0, () -> true),
    MILLILITRE(0.001, () -> true),
    GALLON(3.78541, () -> true);

    final double factor;
    private final SupportsArithmetic supportsArithmetic;

    VolumeUnit(double f, SupportsArithmetic supportsArithmetic) {
        factor = f;
        this.supportsArithmetic = supportsArithmetic;
    }

    @Override
    public boolean isSupported() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        // Volume units support all arithmetic operations
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
