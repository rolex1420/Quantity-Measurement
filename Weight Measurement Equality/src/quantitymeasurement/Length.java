package quantitymeasurement;

public class Length {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 0.0001;

    public Length(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException();
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public Length convertTo(LengthUnit target) {
        double base = unit.convertToBaseUnit(value);
        double result = target.convertFromBaseUnit(base);
        return new Length(result, target);
    }

    public Length add(Length other) {
        return add(this, other, this.unit);
    }

    public static Length add(Length l1, Length l2, LengthUnit target) {
        if (l1 == null || l2 == null || target == null)
            throw new IllegalArgumentException();

        double sum = l1.unit.convertToBaseUnit(l1.value)
                + l2.unit.convertToBaseUnit(l2.value);

        return new Length(target.convertFromBaseUnit(sum), target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length)) return false;

        Length other = (Length) o;

        double a = this.unit.convertToBaseUnit(this.value);
        double b = other.unit.convertToBaseUnit(other.value);

        return Math.abs(a - b) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }
}