package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementAppTest {

    @Test
    public void testEquality_FeetToFeet_SameValue() {
        assertEquals(new Length(1.0, LengthUnit.FEET), new Length(1.0, LengthUnit.FEET));
    }

    @Test
    public void testEquality_InchToInch_SameValue() {
        assertEquals(new Length(1.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.INCHES));
    }

    @Test
    public void testEquality_InchToFeet_EquivalentValue() {
        assertEquals(new Length(12.0, LengthUnit.INCHES), new Length(1.0, LengthUnit.FEET));
    }

    @Test
    public void testEquality_FeetToInch_EquivalentValue() {
        assertEquals(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES));
    }

    @Test
    public void testEquality_FeetToFeet_DifferentValue() {
        assertNotEquals(new Length(1.0, LengthUnit.FEET), new Length(2.0, LengthUnit.FEET));
    }

    @Test
    public void testEquality_InchToInch_DifferentValue() {
        assertNotEquals(new Length(1.0, LengthUnit.INCHES), new Length(2.0, LengthUnit.INCHES));
    }

    @Test
    public void testEquality_SameReference() {
        Length length = new Length(1.0, LengthUnit.FEET);
        assertEquals(length, length);
    }

    @Test
    public void testEquality_NullComparison() {
        assertNotEquals(null, new Length(1.0, LengthUnit.FEET));
    }

    @Test
    public void testEquality_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Length(1.0, null));
    }
}
