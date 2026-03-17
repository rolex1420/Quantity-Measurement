public enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double factorToFeet;

    LengthUnit(double factorToFeet) {
        this.factorToFeet = factorToFeet;
    }

    public double toBase(double value) {
        return value * factorToFeet;
    }

    public double fromBase(double valueInFeet) {
        return valueInFeet / factorToFeet;
    }
}