package model;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    private void validate(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Quantity cannot be null");
        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Different measurement categories");
    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {
        validate(other);
        unit.validateOperationSupport(op.name());
        other.unit.validateOperationSupport(op.name());
        double base1 = unit.toBase(value);
        double base2 = other.unit.toBase(other.value);
        return op.apply(base1, base2);
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    public Quantity<U> add(Quantity<U> other) {
        double result = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(round(unit.fromBase(result)), unit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        double result = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(round(unit.fromBase(result)), unit);
    }

    public double divide(Quantity<U> other) {
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.toBase(value);
        return new Quantity<>(round(targetUnit.fromBase(base)), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Quantity<?> other)) return false;
        if (!this.unit.getClass().equals(other.unit.getClass())) return false;
        double base1 = unit.toBase(value);
        double base2 = ((IMeasurable) other.unit).toBase(other.value);
        return Math.abs(base1 - base2) < 0.0001;
    }
}
