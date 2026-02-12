package com.apps.quantitymeasurement;

/**
 * Generic Length class applying DRY principle.
 */
public class Length {

    private final double value;
    private final LengthUnit unit;

    /**
     * Enum representing supported length units.
     * Base unit is INCHES.
     */
    public enum LengthUnit {

        FEET(12.0),
        INCHES(1.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    public Length(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if(!Double.isFinite(value)){
            throw new IllegalArgumentException("Invalid numeric value");
        }
        this.value = value;
        this.unit = unit;
    }

    private double convertToBaseUnit() {
        return value * unit.getConversionFactor();
    }

    public boolean compare(Length that) {
        return Double.compare(
                this.convertToBaseUnit(),
                that.convertToBaseUnit()
        ) == 0;
    }

    @Override
    public boolean equals(Object obj) {

        // Same reference
        if (this == obj) {
            return true;
        }

        // Null or different class
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Length other = (Length) obj;
        return compare(other);
    }
}