public class QuantityMeasurementApp {

    public static void main(String[] args) {

        System.out.println(
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES)
        );

        System.out.println(
                QuantityLength.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET)
        );

        System.out.println(
                QuantityLength.convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS)
        );

        System.out.println(
                QuantityLength.convert(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES)
        );
    }
}