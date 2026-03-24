package units;

import model.IMeasurable;

public enum VolumeUnit implements IMeasurable {

    LITRE(1000.0),
    MILLILITRE(1.0);

    private final double factor;

    VolumeUnit(double factor) {
        this.factor = factor;
    }

    @Override
    public double toBase(double value) {
        return value * factor;
    }

    @Override
    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}