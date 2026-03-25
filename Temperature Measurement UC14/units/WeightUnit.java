package units;

import model.IMeasurable;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1000.0),
    GRAM(1.0);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}
