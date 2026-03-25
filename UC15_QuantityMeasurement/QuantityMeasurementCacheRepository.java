
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {
    private static QuantityMeasurementCacheRepository instance;
    private List<QuantityMeasurementEntity> data = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {}

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) instance = new QuantityMeasurementCacheRepository();
        return instance;
    }

    public void save(QuantityMeasurementEntity entity) {
        data.add(entity);
    }
}
