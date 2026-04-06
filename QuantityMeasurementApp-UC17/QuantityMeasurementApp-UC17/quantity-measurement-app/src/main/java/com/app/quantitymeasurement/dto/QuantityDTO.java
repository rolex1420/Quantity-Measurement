package com.app.quantitymeasurement.dto;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
public class QuantityDTO {

    @NotNull(message = "Value cannot be null")
    private Double value;

    @NotEmpty(message = "Unit cannot be empty")
    @Pattern(regexp = "^(FEET|INCHES|YARD|MILE|MILLIMETER|CENTIMETER|METER|KILOMETER|GALLON|LITER|MILLILITER|OUNCE|POUND|KILOGRAM|GRAM|CELSIUS|FAHRENHEIT|KELVIN)$", 
             message = "Unit must be valid for the specified measurement type")
    private String unit;

    @NotEmpty(message = "Measurement type cannot be empty")
    @Pattern(regexp = "^(LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit)$", 
             message = "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit")
    private String measurementType;
}
