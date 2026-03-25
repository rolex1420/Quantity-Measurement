package units;

import model.IMeasurable;

public enum LengthUnit implements IMeasurable {

    FEET(12.0),
    INCHES(1.0);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}
