
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    public QuantityDTO convert(QuantityDTO input, String toUnit) {
        if (input.unit.equals("FEET")) {
            LengthUnit from = LengthUnit.FEET;
            LengthUnit to = LengthUnit.valueOf(toUnit);
            double base = from.toBase(input.value);
            return new QuantityDTO(to.fromBase(base), toUnit);
        }

        if (input.unit.equals("CELSIUS")) {
            TemperatureUnit from = TemperatureUnit.CELSIUS;
            TemperatureUnit to = TemperatureUnit.valueOf(toUnit);
            double base = from.toBase(input.value);
            return new QuantityDTO(to.fromBase(base), toUnit);
        }

        throw new RuntimeException("Unsupported unit");
    }
}
