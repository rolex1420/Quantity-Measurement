package quantitymeasurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(l1.add(l2));
        System.out.println(l1.add(l2, LengthUnit.INCHES));
        System.out.println(l1.add(l2, LengthUnit.YARDS));

        Length l3 = new Length(36.0, LengthUnit.INCHES);
        Length l4 = new Length(1.0, LengthUnit.YARDS);

        System.out.println(l3.equals(l4));

        Length l5 = new Length(2.54, LengthUnit.CENTIMETERS);
        System.out.println(l5.convertTo(LengthUnit.INCHES));
    }
}