package quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testConvert_FeetToInches() {
        Length l = new Length(1.0, LengthUnit.FEET);
        Length result = l.convertTo(LengthUnit.INCHES);
        assertTrue(result.equals(new Length(12.0, LengthUnit.INCHES)));
    }

    @Test
    void testConvert_InchesToFeet() {
        Length l = new Length(24.0, LengthUnit.INCHES);
        Length result = l.convertTo(LengthUnit.FEET);
        assertTrue(result.equals(new Length(2.0, LengthUnit.FEET)));
    }

    @Test
    void testEquality_FeetAndInches() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_YardAndFeet() {
        Length l1 = new Length(1.0, LengthUnit.YARDS);
        Length l2 = new Length(3.0, LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testAddition_SameUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(2.0, LengthUnit.FEET);
        Length result = l1.add(l2);
        assertEquals(new Length(3.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_CrossUnit_FeetInches() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2);
        assertEquals(new Length(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_CrossUnit_InchesFeet() {
        Length l1 = new Length(12.0, LengthUnit.INCHES);
        Length l2 = new Length(1.0, LengthUnit.FEET);
        Length result = l1.add(l2);
        assertEquals(new Length(24.0, LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_TargetUnit_Feet() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.FEET);
        assertEquals(new Length(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_TargetUnit_Inches() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.INCHES);
        assertEquals(new Length(24.0, LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_TargetUnit_Yards() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.YARDS);
        assertTrue(result.equals(new Length(0.6667, LengthUnit.YARDS)));
    }

    @Test
    void testAddition_CentimeterAndInch() {
        Length l1 = new Length(2.54, LengthUnit.CENTIMETERS);
        Length l2 = new Length(1.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.CENTIMETERS);
        assertTrue(result.equals(new Length(5.08, LengthUnit.CENTIMETERS)));
    }

    @Test
    void testAddition_Commutativity() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length r1 = l1.add(l2, LengthUnit.YARDS);
        Length r2 = l2.add(l1, LengthUnit.YARDS);

        assertTrue(r1.equals(r2));
    }

    @Test
    void testAddition_WithZero() {
        Length l1 = new Length(5.0, LengthUnit.FEET);
        Length l2 = new Length(0.0, LengthUnit.INCHES);
        Length result = l1.add(l2);
        assertEquals(new Length(5.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_NegativeValues() {
        Length l1 = new Length(5.0, LengthUnit.FEET);
        Length l2 = new Length(-2.0, LengthUnit.FEET);
        Length result = l1.add(l2, LengthUnit.INCHES);
        assertEquals(new Length(36.0, LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_LargeValues() {
        Length l1 = new Length(1e6, LengthUnit.FEET);
        Length l2 = new Length(1e6, LengthUnit.FEET);
        Length result = l1.add(l2);
        assertEquals(new Length(2e6, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_SmallValues() {
        Length l1 = new Length(0.001, LengthUnit.FEET);
        Length l2 = new Length(0.002, LengthUnit.FEET);
        Length result = l1.add(l2);
        assertTrue(result.equals(new Length(0.003, LengthUnit.FEET)));
    }

    @Test
    void testNullInput() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> {
            l1.add(null);
        });
    }

    @Test
    void testNullTargetUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> {
            l1.add(l2, null);
        });
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(Double.NaN, LengthUnit.FEET);
        });
    }
}