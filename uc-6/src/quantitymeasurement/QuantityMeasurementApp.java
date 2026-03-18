package quantitymeasurement;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static double demonstrateLengthConversion(double value,
                                                     Length.LengthUnit from,
                                                     Length.LengthUnit to) {
        return Length.convert(value, from, to);
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        return l1.add(l2);
    }

    public static Length demonstrateLengthAddition(Length l1,
                                                   Length l2,
                                                   Length.LengthUnit target) {
        return Length.add(l1, l2, target);
    }
    public static void main(String[] args) {

        System.out.println(Length.add(1.0, Length.LengthUnit.FEET,
                2.0, Length.LengthUnit.FEET,
                Length.LengthUnit.FEET));

        System.out.println(Length.add(1.0, Length.LengthUnit.FEET,
                12.0, Length.LengthUnit.INCHES,
                Length.LengthUnit.FEET));

        System.out.println(Length.add(12.0, Length.LengthUnit.INCHES,
                1.0, Length.LengthUnit.FEET,
                Length.LengthUnit.INCHES));

        System.out.println(Length.add(1.0, Length.LengthUnit.YARDS,
                3.0, Length.LengthUnit.FEET,
                Length.LengthUnit.YARDS));

        System.out.println(Length.add(36.0, Length.LengthUnit.INCHES,
                1.0, Length.LengthUnit.YARDS,
                Length.LengthUnit.INCHES));

        System.out.println(Length.add(2.54, Length.LengthUnit.CENTIMETERS,
                1.0, Length.LengthUnit.INCHES,
                Length.LengthUnit.CENTIMETERS));

        System.out.println(Length.add(5.0, Length.LengthUnit.FEET,
                0.0, Length.LengthUnit.INCHES,
                Length.LengthUnit.FEET));

        System.out.println(Length.add(5.0, Length.LengthUnit.FEET,
                -2.0, Length.LengthUnit.FEET,
                Length.LengthUnit.FEET));
    }
}