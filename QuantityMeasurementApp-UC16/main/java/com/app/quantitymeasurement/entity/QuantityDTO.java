package com.app.quantitymeasurement.entity;

public class QuantityDTO {
    private double value;
    private String unit;
    private String measurementType;

    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        FEET("FEET", "LENGTH"),
        INCHES("INCHES", "LENGTH"),
        YARDS("YARDS", "LENGTH"),
        CM("CM", "LENGTH"),
        CENTIMETERS("CENTIMETERS", "LENGTH");

        private final String unitName;
        private final String measurementType;

        LengthUnit(String unitName, String measurementType) {
            this.unitName = unitName;
            this.measurementType = measurementType;
        }

        @Override
        public String getUnitName() {
            return unitName;
        }

        @Override
        public String getMeasurementType() {
            return measurementType;
        }
    }

    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAM("KILOGRAM", "WEIGHT"),
        GRAM("GRAM", "WEIGHT"),
        POUND("POUND", "WEIGHT");

        private final String unitName;
        private final String measurementType;

        WeightUnit(String unitName, String measurementType) {
            this.unitName = unitName;
            this.measurementType = measurementType;
        }

        @Override
        public String getUnitName() {
            return unitName;
        }

        @Override
        public String getMeasurementType() {
            return measurementType;
        }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE("LITRE", "VOLUME"),
        MILLILITRE("MILLILITRE", "VOLUME"),
        GALLON("GALLON", "VOLUME");

        private final String unitName;
        private final String measurementType;

        VolumeUnit(String unitName, String measurementType) {
            this.unitName = unitName;
            this.measurementType = measurementType;
        }

        @Override
        public String getUnitName() {
            return unitName;
        }

        @Override
        public String getMeasurementType() {
            return measurementType;
        }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS("CELSIUS", "TEMPERATURE"),
        FAHRENHEIT("FAHRENHEIT", "TEMPERATURE"),
        KELVIN("KELVIN", "TEMPERATURE");

        private final String unitName;
        private final String measurementType;

        TemperatureUnit(String unitName, String measurementType) {
            this.unitName = unitName;
            this.measurementType = measurementType;
        }

        @Override
        public String getUnitName() {
            return unitName;
        }

        @Override
        public String getMeasurementType() {
            return measurementType;
        }
    }

    public QuantityDTO() {}

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    @Override
    public String toString() {
        return value == (int)value ? value + " " + unit : String.format("%.2f %s", value, unit);
    }
}
