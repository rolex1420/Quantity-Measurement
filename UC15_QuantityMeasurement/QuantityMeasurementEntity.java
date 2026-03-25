
import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {
    public String operation;
    public String result;

    public QuantityMeasurementEntity(String operation, String result) {
        this.operation = operation;
        this.result = result;
    }
}
