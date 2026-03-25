
public class QuantityMeasurementApp {
    public static void main(String[] args) {
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl();
        QuantityMeasurementController controller = new QuantityMeasurementController(service);

        controller.performConversion(new QuantityDTO(10, "FEET"), "METER");
        controller.performConversion(new QuantityDTO(100, "CELSIUS"), "FAHRENHEIT");
    }
}
