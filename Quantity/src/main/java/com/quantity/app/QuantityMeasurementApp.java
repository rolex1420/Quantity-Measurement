package com.quantity.app;

import com.quantity.model.Quantity;
import com.quantity.unit.VolumeUnit;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        System.out.println("Equality: " + v1.equals(v2));
        System.out.println("Convert: " + v3.convertTo(VolumeUnit.LITRE));
        System.out.println("Add: " + v1.add(v2));
    }
}
