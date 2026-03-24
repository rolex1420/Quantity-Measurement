package app;

import model.Quantity;
import units.LengthUnit;
import units.WeightUnit;
import units.VolumeUnit;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<LengthUnit> l1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6, LengthUnit.INCHES);

        System.out.println("Length Subtract: " + l1.subtract(l2));
        System.out.println("Length Divide: " + l1.divide(new Quantity<>(2, LengthUnit.FEET)));

        Quantity<WeightUnit> w1 = new Quantity<>(10, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000, WeightUnit.GRAM);

        System.out.println("Weight Subtract: " + w1.subtract(w2));

        Quantity<VolumeUnit> v1 = new Quantity<>(5, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500, VolumeUnit.MILLILITRE);

        System.out.println("Volume Subtract: " + v1.subtract(v2));
    }
}