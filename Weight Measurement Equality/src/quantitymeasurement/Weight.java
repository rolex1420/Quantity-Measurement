package quantitymeasurement;

public class Weight {
    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 0.0001;

    public Weight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException();
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public Weight convertTo(WeightUnit target) {
        double base = unit.convertToBaseUnit(value);
        return new Weight(target.convertFromBaseUnit(base), target);
    }

    public Weight add(Weight other) {
        return add(this, other, this.unit);
    }

    public static Weight add(Weight w1, Weight w2, WeightUnit target) {
        if (w1 == null || w2 == null || target == null)
            throw new IllegalArgumentException();

        double sum = w1.unit.convertToBaseUnit(w1.value)
                + w2.unit.convertToBaseUnit(w2.value);

        return new Weight(target.convertFromBaseUnit(sum), target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weight)) return false;

        Weight other = (Weight) o;

        double a = this.unit.convertToBaseUnit(this.value);
        double b = other.unit.convertToBaseUnit(other.value);

        return Math.abs(a - b) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }
}