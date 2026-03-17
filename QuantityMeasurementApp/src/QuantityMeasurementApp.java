public class QuantityMeasurementApp {

    enum LengthUnit {

        FEET(1.0),

        INCH(1.0 / 12.0),                 // 1 inch = 1/12 feet

        YARD(3.0),                        // 1 yard = 3 feet

        CENTIMETER(0.393701 / 12.0);      // 1 cm = 0.393701 inch → convert to feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    public static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }
    }

    public static void main(String[] args) {

        var q1 = new QuantityLength(1.0, LengthUnit.YARD);
        var q2 = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("Input: Quantity(1.0, YARDS) and Quantity(3.0, FEET)");
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        var q3 = new QuantityLength(1.0, LengthUnit.YARD);
        var q4 = new QuantityLength(36.0, LengthUnit.INCH);

        System.out.println("\nInput: Quantity(1.0, YARDS) and Quantity(36.0, INCHES)");
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");

        var q5 = new QuantityLength(1.0, LengthUnit.CENTIMETER);
        var q6 = new QuantityLength(0.393701, LengthUnit.INCH);

        System.out.println("\nInput: Quantity(1.0, CM) and Quantity(0.393701, INCH)");
        System.out.println("Output: Equal (" + q5.equals(q6) + ")");
    }
}