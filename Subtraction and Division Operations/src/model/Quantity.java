package model;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOperand(other);
        validateTargetUnit(targetUnit);

        double thisBase = unit.toBase(value);
        double otherBase = other.unit.toBase(other.value);

        double resultBase = thisBase - otherBase;

        double result = targetUnit.fromBase(resultBase);

        return new Quantity<>(round(result), targetUnit);
    }

    public double divide(Quantity<U> other) {
        validateOperand(other);

        double thisBase = unit.toBase(value);
        double otherBase = other.unit.toBase(other.value);

        if (otherBase == 0) {
            throw new ArithmeticException("Division by zero not allowed");
        }

        return thisBase / otherBase;
    }

    private void validateOperand(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        if (this.unit.getClass() != other.unit.getClass()) {
            throw new IllegalArgumentException("Cross-category operation not allowed");
        }

        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid value");
        }
    }

    private void validateTargetUnit(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}