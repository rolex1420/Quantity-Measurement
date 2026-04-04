package com.app.quantitymeasurement.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable, SupportsArithmetic {
    CELSIUS(1.0, celsius -> celsius, celsius -> celsius, "CELSIUS"),
    FAHRENHEIT(1.0, fahrenheit -> (fahrenheit - 32.0) * 5.0/9.0, celsius -> (celsius * 9.0/5.0) + 32.0, "FAHRENHEIT"),
    KELVIN(1.0, kelvin -> kelvin - 273.15, celsius -> celsius + 273.15, "KELVIN");

    private final double factor;
    private final Function<Double, Double> toCelsius;
    private final Function<Double, Double> fromCelsius;
    private final String unitName;

    TemperatureUnit(double factor, Function<Double, Double> toCelsius, Function<Double, Double> fromCelsius, String unitName) {
        this.factor = factor;
        this.toCelsius = toCelsius;
        this.fromCelsius = fromCelsius;
        this.unitName = unitName;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        return toCelsius.apply(value);
    }

    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) throw new IllegalArgumentException();
        return fromCelsius.apply(baseValue);
    }

    public String getUnitName() {
        return unitName;
    }

    @Override
    public boolean isSupported() {
        return false;
    }

    @Override
    public boolean supportsArithmetic() {
        return isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        if (!supportsArithmetic()) {
            throw new UnsupportedOperationException("Temperature does not support " + operation);
        }
    }
}
