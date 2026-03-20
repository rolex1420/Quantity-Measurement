package quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testWeightEquality_KgToGram() {
        assertTrue(new Weight(1, WeightUnit.KILOGRAM)
                .equals(new Weight(1000, WeightUnit.GRAM)));
    }

    @Test
    void testWeightEquality_KgToPound() {
        assertTrue(new Weight(1, WeightUnit.KILOGRAM)
                .equals(new Weight(2.20462, WeightUnit.POUND)));
    }

    @Test
    void testWeightConversion() {
        Weight result = new Weight(1, WeightUnit.KILOGRAM)
                .convertTo(WeightUnit.GRAM);
        assertEquals(new Weight(1000, WeightUnit.GRAM), result);
    }

    @Test
    void testWeightAddition_SameUnit() {
        Weight result = new Weight(1, WeightUnit.KILOGRAM)
                .add(new Weight(2, WeightUnit.KILOGRAM));
        assertEquals(new Weight(3, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testWeightAddition_CrossUnit() {
        Weight result = new Weight(1, WeightUnit.KILOGRAM)
                .add(new Weight(1000, WeightUnit.GRAM));
        assertEquals(new Weight(2, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testWeightAddition_TargetUnit() {
        Weight result = Weight.add(
                new Weight(1, WeightUnit.KILOGRAM),
                new Weight(1000, WeightUnit.GRAM),
                WeightUnit.GRAM
        );
        assertEquals(new Weight(2000, WeightUnit.GRAM), result);
    }

    @Test
    void testWeightZero() {
        Weight result = new Weight(5, WeightUnit.KILOGRAM)
                .add(new Weight(0, WeightUnit.GRAM));
        assertEquals(new Weight(5, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testWeightNegative() {
        Weight result = new Weight(5, WeightUnit.KILOGRAM)
                .add(new Weight(-2000, WeightUnit.GRAM));
        assertEquals(new Weight(3, WeightUnit.KILOGRAM), result);
    }

    @Test
    void testLengthVsWeight() {
        assertFalse(new Weight(1, WeightUnit.KILOGRAM)
                .equals(new Length(1, LengthUnit.FEET)));
    }
}