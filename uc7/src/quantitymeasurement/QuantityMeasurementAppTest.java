package quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        Length result = Length.add(
                new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.FEET
        );

        assertEquals(new Length(2.0, Length.LengthUnit.FEET), result);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        Length result = Length.add(
                new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.INCHES
        );

        assertEquals(new Length(24.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        Length result = Length.add(
                new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.YARDS
        );

        assertTrue(result.equals(new Length(0.6667, Length.LengthUnit.YARDS)));
    }

    @Test
    void testAddition_Commutativity() {
        Length r1 = Length.add(
                new Length(1.0, Length.LengthUnit.FEET),
                new Length(12.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.YARDS
        );

        Length r2 = Length.add(
                new Length(12.0, Length.LengthUnit.INCHES),
                new Length(1.0, Length.LengthUnit.FEET),
                Length.LengthUnit.YARDS
        );

        assertTrue(r1.equals(r2));
    }

    @Test
    void testAddition_NullTargetUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            Length.add(
                    new Length(1.0, Length.LengthUnit.FEET),
                    new Length(12.0, Length.LengthUnit.INCHES),
                    null
            );
        });
    }

    @Test
    void testAddition_NegativeValues() {
        Length result = Length.add(
                new Length(5.0, Length.LengthUnit.FEET),
                new Length(-2.0, Length.LengthUnit.FEET),
                Length.LengthUnit.INCHES
        );

        assertEquals(new Length(36.0, Length.LengthUnit.INCHES), result);
    }

    @Test
    void testAddition_ZeroValue() {
        Length result = Length.add(
                new Length(5.0, Length.LengthUnit.FEET),
                new Length(0.0, Length.LengthUnit.INCHES),
                Length.LengthUnit.YARDS
        );

        assertTrue(result.equals(new Length(1.6667, Length.LengthUnit.YARDS)));
    }
}