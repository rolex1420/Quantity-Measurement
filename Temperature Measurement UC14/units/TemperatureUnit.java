package units;

import model.IMeasurable;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        public double toBase(double value) { return value; }
        public double fromBase(double baseValue) { return baseValue; }
    },

    FAHRENHEIT {
        public double toBase(double value) { return (value - 32) * 5 / 9; }
        public double fromBase(double baseValue) { return (baseValue * 9 / 5) + 32; }
    };

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException("Temperature does not support " + operation);
    }
}
