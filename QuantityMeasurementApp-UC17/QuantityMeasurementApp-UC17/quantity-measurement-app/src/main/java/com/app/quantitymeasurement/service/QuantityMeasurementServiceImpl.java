package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Override
    public QuantityMeasurementDTO compareQuantities(QuantityInputDTO input) {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setThisValue(input.getThisQuantityDTO().getValue());
        result.setThisUnit(input.getThisQuantityDTO().getUnit());
        result.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        result.setThatValue(input.getThatQuantityDTO().getValue());
        result.setThatUnit(input.getThatQuantityDTO().getUnit());
        result.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());
        result.setOperation(OperationType.COMPARE);

        try {
            if (!input.getThisQuantityDTO().getMeasurementType().equals(input.getThatQuantityDTO().getMeasurementType())) {
                throw new RuntimeException("Cannot compare different measurement types");
            }

            double thisValueInBase = convertToBaseUnit(input.getThisQuantityDTO());
            double thatValueInBase = convertToBaseUnit(input.getThatQuantityDTO());

            boolean comparisonResult = Double.compare(thisValueInBase, thatValueInBase) == 0;
            result.setResultString(String.valueOf(comparisonResult));
            result.setError(false);

            repository.save(result.toEntity());
            return result;

        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            result.setError(true);
            repository.save(result.toEntity());
            return result;
        }
    }

    @Override
    public QuantityMeasurementDTO convertQuantity(QuantityInputDTO input) {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setThisValue(input.getThisQuantityDTO().getValue());
        result.setThisUnit(input.getThisQuantityDTO().getUnit());
        result.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        result.setThatValue(input.getThatQuantityDTO().getValue());
        result.setThatUnit(input.getThatQuantityDTO().getUnit());
        result.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());
        result.setOperation(OperationType.CONVERT);

        try {
            if (!input.getThisQuantityDTO().getMeasurementType().equals(input.getThatQuantityDTO().getMeasurementType())) {
                throw new RuntimeException("Cannot convert between different measurement types");
            }

            double thisValueInBase = convertToBaseUnit(input.getThisQuantityDTO());
            double convertedValue = convertFromBaseUnit(thisValueInBase, input.getThatQuantityDTO().getUnit(), input.getThisQuantityDTO().getMeasurementType());

            result.setResultValue(convertedValue);
            result.setError(false);

            repository.save(result.toEntity());
            return result;

        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            result.setError(true);
            repository.save(result.toEntity());
            return result;
        }
    }

    @Override
    public QuantityMeasurementDTO addQuantities(QuantityInputDTO input) {
        return performArithmeticOperation(input, OperationType.ADD, (a, b) -> a + b);
    }

    @Override
    public QuantityMeasurementDTO subtractQuantities(QuantityInputDTO input) {
        return performArithmeticOperation(input, OperationType.SUBTRACT, (a, b) -> a - b);
    }

    @Override
    public QuantityMeasurementDTO multiplyQuantities(QuantityInputDTO input) {
        return performArithmeticOperation(input, OperationType.MULTIPLY, (a, b) -> a * b);
    }

    @Override
    public QuantityMeasurementDTO divideQuantities(QuantityInputDTO input) {
        return performArithmeticOperation(input, OperationType.DIVIDE, (a, b) -> {
            if (b == 0) throw new RuntimeException("Divide by zero");
            return a / b;
        });
    }

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(OperationType operation) {
        List<QuantityMeasurementEntity> entities = repository.findByOperation(operation);
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType) {
        List<QuantityMeasurementEntity> entities = repository.findByThisMeasurementType(measurementType);
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    @Override
    public long getOperationCount(OperationType operation) {
        return repository.countByOperationAndIsErrorFalse(operation);
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        List<QuantityMeasurementEntity> entities = repository.findByIsErrorTrue();
        return QuantityMeasurementDTO.fromEntityList(entities);
    }

    private QuantityMeasurementDTO performArithmeticOperation(QuantityInputDTO input, OperationType operation, 
            ArithmeticOperation operationFunc) {
        QuantityMeasurementDTO result = new QuantityMeasurementDTO();
        result.setThisValue(input.getThisQuantityDTO().getValue());
        result.setThisUnit(input.getThisQuantityDTO().getUnit());
        result.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        result.setThatValue(input.getThatQuantityDTO().getValue());
        result.setThatUnit(input.getThatQuantityDTO().getUnit());
        result.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());
        result.setOperation(operation);

        try {
            if (!input.getThisQuantityDTO().getMeasurementType().equals(input.getThatQuantityDTO().getMeasurementType())) {
                throw new RuntimeException("Cannot perform arithmetic between different measurement categories: " 
                    + input.getThisQuantityDTO().getMeasurementType() + " and " + input.getThatQuantityDTO().getMeasurementType());
            }

            double thisValueInBase = convertToBaseUnit(input.getThisQuantityDTO());
            double thatValueInBase = convertToBaseUnit(input.getThatQuantityDTO());

            double resultValue = operationFunc.apply(thisValueInBase, thatValueInBase);
            double resultInThisUnit = convertFromBaseUnit(resultValue, input.getThisQuantityDTO().getUnit(), 
                input.getThisQuantityDTO().getMeasurementType());

            result.setResultValue(resultInThisUnit);
            result.setResultUnit(input.getThisQuantityDTO().getUnit());
            result.setResultMeasurementType(input.getThisQuantityDTO().getMeasurementType());
            result.setError(false);

            repository.save(result.toEntity());
            return result;

        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
            result.setError(true);
            repository.save(result.toEntity());
            return result;
        }
    }

    private double convertToBaseUnit(QuantityDTO quantity) {
        String unit = quantity.getUnit();
        String type = quantity.getMeasurementType();
        double value = quantity.getValue();

        switch (type) {
            case "LengthUnit":
                switch (unit) {
                    case "FEET": return value * 0.3048;
                    case "INCHES": return value * 0.0254;
                    case "YARD": return value * 0.9144;
                    case "MILE": return value * 1609.34;
                    case "MILLIMETER": return value / 1000;
                    case "CENTIMETER": return value / 100;
                    case "KILOMETER": return value * 1000;
                    case "METER": return value;
                    default: throw new RuntimeException("Unknown length unit: " + unit);
                }
            case "VolumeUnit":
                switch (unit) {
                    case "GALLON": return value * 3.78541;
                    case "LITER": return value;
                    case "MILLILITER": return value / 1000;
                    case "OUNCE": return value * 0.0295735;
                    default: throw new RuntimeException("Unknown volume unit: " + unit);
                }
            case "WeightUnit":
                switch (unit) {
                    case "POUND": return value * 0.453592;
                    case "KILOGRAM": return value;
                    case "GRAM": return value / 1000;
                    case "OUNCE": return value * 0.0283495;
                    default: throw new RuntimeException("Unknown weight unit: " + unit);
                }
            case "TemperatureUnit":
                switch (unit) {
                    case "CELSIUS": return value;
                    case "FAHRENHEIT": return (value - 32) * 5/9;
                    case "KELVIN": return value - 273.15;
                    default: throw new RuntimeException("Unknown temperature unit: " + unit);
                }
            default:
                throw new RuntimeException("Unknown measurement type: " + type);
        }
    }

    private double convertFromBaseUnit(double baseValue, String targetUnit, String type) {
        switch (type) {
            case "LengthUnit":
                switch (targetUnit) {
                    case "FEET": return baseValue / 0.3048;
                    case "INCHES": return baseValue / 0.0254;
                    case "YARD": return baseValue / 0.9144;
                    case "MILE": return baseValue / 1609.34;
                    case "MILLIMETER": return baseValue * 1000;
                    case "CENTIMETER": return baseValue * 100;
                    case "KILOMETER": return baseValue / 1000;
                    case "METER": return baseValue;
                    default: throw new RuntimeException("Unknown length unit: " + targetUnit);
                }
            case "VolumeUnit":
                switch (targetUnit) {
                    case "GALLON": return baseValue / 3.78541;
                    case "LITER": return baseValue;
                    case "MILLILITER": return baseValue * 1000;
                    case "OUNCE": return baseValue / 0.0295735;
                    default: throw new RuntimeException("Unknown volume unit: " + targetUnit);
                }
            case "WeightUnit":
                switch (targetUnit) {
                    case "POUND": return baseValue / 0.453592;
                    case "KILOGRAM": return baseValue;
                    case "GRAM": return baseValue * 1000;
                    case "OUNCE": return baseValue / 0.0283495;
                    default: throw new RuntimeException("Unknown weight unit: " + targetUnit);
                }
            case "TemperatureUnit":
                switch (targetUnit) {
                    case "CELSIUS": return baseValue;
                    case "FAHRENHEIT": return baseValue * 9/5 + 32;
                    case "KELVIN": return baseValue + 273.15;
                    default: throw new RuntimeException("Unknown temperature unit: " + targetUnit);
                }
            default:
                throw new RuntimeException("Unknown measurement type: " + type);
        }
    }

    @FunctionalInterface
    private interface ArithmeticOperation {
        double apply(double a, double b);
    }
}
