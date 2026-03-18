package quantitymeasurement;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    private static final double EPSILON = 0.0001;
    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

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

    public double toBaseUnit() {
        return unit.toFeet(value);
    }

    private static double fromBaseUnit(double valueInFeet, LengthUnit targetUnit) {
        return targetUnit.fromFeet(valueInFeet);
    }

    public Length add(Length other) {
        if (other == null) {
            throw new IllegalArgumentException("Length cannot be null");
        }

        double sum = this.toBaseUnit() + other.toBaseUnit();
        double result = fromBaseUnit(sum, this.unit);

        return new Length(result, this.unit);
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

        double diff = Math.abs(this.toBaseUnit() - other.toBaseUnit());

        return diff < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}