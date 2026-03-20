package quantitymeasurement;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    public Length(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    private static double fromBaseUnit(double baseValue, LengthUnit targetUnit) {
        return targetUnit.convertFromBaseUnit(baseValue);
    }

    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double base = toBaseUnit();
        double result = fromBaseUnit(base, targetUnit);
        return new Length(result, targetUnit);
    }

    public Length add(Length other) {
        if (other == null) {
            throw new IllegalArgumentException("Length cannot be null");
        }
        double sum = this.toBaseUnit() + other.toBaseUnit();
        double result = fromBaseUnit(sum, this.unit);
        return new Length(result, this.unit);
    }

    public Length add(Length other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        double sum = this.toBaseUnit() + other.toBaseUnit();
        double result = fromBaseUnit(sum, targetUnit);
        return new Length(result, targetUnit);
    }

    public static Length add(Length l1, Length l2, LengthUnit targetUnit) {
        if (l1 == null || l2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        double sum = l1.toBaseUnit() + l2.toBaseUnit();
        double result = fromBaseUnit(sum, targetUnit);
        return new Length(result, targetUnit);
    }

    public static Length add(double v1, LengthUnit u1,
                             double v2, LengthUnit u2,
                             LengthUnit targetUnit) {
        return add(new Length(v1, u1), new Length(v2, u2), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Length)) return false;
        Length other = (Length) obj;
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 1e-6;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBaseUnit() * 1e6));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}