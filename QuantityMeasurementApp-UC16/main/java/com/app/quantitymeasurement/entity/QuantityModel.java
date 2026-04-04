package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.unit.IMeasurable;

public class QuantityModel<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public QuantityModel(double value, U unit) {
        if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    @SuppressWarnings("unchecked")
    public QuantityModel<U> to(U targetUnit) {
        return new QuantityModel<>(convert(value, unit, targetUnit), targetUnit);
    }

    @SuppressWarnings("unchecked")
    public QuantityModel<U> add(QuantityModel<U> other) {
        return new QuantityModel<>(add(value, unit, other.value, other.unit), unit);
    }

    @SuppressWarnings("unchecked")
    public QuantityModel<U> subtract(QuantityModel<U> other) {
        return new QuantityModel<>(subtract(value, unit, other.value, other.unit), unit);
    }

    public double divide(QuantityModel<U> other) {
        return divide(value, unit, other.value, other.unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityModel)) return false;
        QuantityModel<?> that = (QuantityModel<?>) o;
        if (!this.unit.getClass().equals(that.unit.getClass())) return false;
        return Math.abs(unit.convertToBaseUnit(value) - that.unit.convertToBaseUnit(that.value)) < 1e-5;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return value == (int)value ? value + " " + unit.getUnitName() : String.format("%.2f %s", value, unit.getUnitName());
    }

    private static <T extends IMeasurable> double convert(double v, T s, T t) {
        if (s == null || t == null || !Double.isFinite(v)) throw new IllegalArgumentException();
        double baseValue = s.convertToBaseUnit(v);
        return t.convertFromBaseUnit(baseValue);
    }

    private static <T extends IMeasurable> double add(double v1, T u1, double v2, T u2) {
        double base1 = u1.convertToBaseUnit(v1);
        double base2 = u2.convertToBaseUnit(v2);
        return u1.convertFromBaseUnit(base1 + base2);
    }

    private static <T extends IMeasurable> double subtract(double v1, T u1, double v2, T u2) {
        double base1 = u1.convertToBaseUnit(v1);
        double base2 = u2.convertToBaseUnit(v2);
        return u1.convertFromBaseUnit(base1 - base2);
    }

    private static <T extends IMeasurable> double divide(double v1, T u1, double v2, T u2) {
        double base1 = u1.convertToBaseUnit(v1);
        double base2 = u2.convertToBaseUnit(v2);
        if (Math.abs(base2) < 1e-10) throw new ArithmeticException("Division by zero");
        return base1 / base2;
    }
}
