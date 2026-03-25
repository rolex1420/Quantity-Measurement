package app;

import model.Quantity;
import units.*;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<LengthUnit> l1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6, LengthUnit.INCHES);

        System.out.println(l1.subtract(l2));

        Quantity<TemperatureUnit> t1 = new Quantity<>(0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32, TemperatureUnit.FAHRENHEIT);

        System.out.println(t1.equals(t2));
    }
}
