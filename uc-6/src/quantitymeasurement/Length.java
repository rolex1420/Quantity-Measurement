package quantitymeasurement;

public final class Length {

    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / toFeetFactor;
        }
    }

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
        return unit.toFeet(value);
    }

    private static double fromBaseUnit(double valueInFeet, LengthUnit target) {
        return target.fromFeet(valueInFeet);
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        double inFeet = source.toFeet(value);
        return target.fromFeet(inFeet);
    }

    public Length convertTo(LengthUnit target) {
        double result = convert(this.value, this.unit, target);
        return new Length(result, target);
    }

    public Length add(Length other) {

        if (other == null) {
            throw new IllegalArgumentException("Length cannot be null");
        }

        double sumInFeet = this.toBaseUnit() + other.toBaseUnit();

        double resultValue = fromBaseUnit(sumInFeet, this.unit);

        return new Length(resultValue, this.unit);
    }

    public static Length add(Length l1, Length l2, LengthUnit targetUnit) {

        if (l1 == null || l2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double sumInFeet = l1.toBaseUnit() + l2.toBaseUnit();

        double resultValue = fromBaseUnit(sumInFeet, targetUnit);

        return new Length(resultValue, targetUnit);
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
        return diff < 1e-6;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(Math.round(toBaseUnit() * 1e6));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}