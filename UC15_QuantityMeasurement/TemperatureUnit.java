
public enum TemperatureUnit implements IMeasurable {
    CELSIUS, FAHRENHEIT;

    public double toBase(double value) {
        if (this == CELSIUS) return value;
        return (value - 32) * 5/9;
    }

    public double fromBase(double baseValue) {
        if (this == CELSIUS) return baseValue;
        return baseValue * 9/5 + 32;
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException("Temperature does not support " + operation);
    }
}
