
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performConversion(QuantityDTO dto, String toUnit) {
        QuantityDTO result = service.convert(dto, toUnit);
        System.out.println("Converted: " + result.value + " " + result.unit);
    }
}
