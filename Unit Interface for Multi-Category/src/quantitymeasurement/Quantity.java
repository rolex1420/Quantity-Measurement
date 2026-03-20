package quantitymeasurement;

public class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;
    private static final double EPSILON = 0.0001;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException();
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public Quantity<U> convertTo(U target) {
        double base = unit.convertToBaseUnit(value);
        double result = target.convertFromBaseUnit(base);
        return new Quantity<>(result, target);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U target) {
        if (other == null || target == null) throw new IllegalArgumentException();

        double sum = unit.convertToBaseUnit(value)
                + other.unit.convertToBaseUnit(other.value);

        double result = target.convertFromBaseUnit(sum);
        return new Quantity<>(result, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quantity<?> other = (Quantity<?>) o;

        if (this.unit.getClass() != other.unit.getClass()) return false;

        double a = this.unit.convertToBaseUnit(this.value);
        double b = other.unit.convertToBaseUnit(other.value);

        return Math.abs(a - b) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}