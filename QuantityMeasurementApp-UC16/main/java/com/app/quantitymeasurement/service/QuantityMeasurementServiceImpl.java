package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.WeightUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        System.out.println("QuantityMeasurementService initialized with repository: " + repository.getClass().getSimpleName());
    }

    @Override
    public QuantityMeasurementEntity compareQuantities(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Comparing quantities: " + quantity1 + " and " + quantity2);
        try {
            validateQuantities(quantity1, quantity2);
            QuantityModel<?> model1 = convertToModel(quantity1);
            QuantityModel<?> model2 = convertToModel(quantity2);
            
            boolean areEqual = model1.equals(model2);
            QuantityDTO result = new QuantityDTO(areEqual ? 1.0 : 0.0, "BOOLEAN", "COMPARISON");
            
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "COMPARE", result);
            repository.save(entity);
            System.out.println("Comparison completed successfully: " + areEqual);
            return entity;
        } catch (Exception e) {
            System.err.println("Error during comparison: " + e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "COMPARE", e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    public QuantityMeasurementEntity convertQuantity(QuantityDTO quantity, String targetUnit) {
        System.out.println("Converting quantity: " + quantity + " to unit: " + targetUnit);
        try {
            validateQuantity(quantity);
            QuantityModel<?> model = convertToModel(quantity);
            IMeasurable targetUnitEnum = getUnitFromName(targetUnit, quantity.getMeasurementType());
            
            @SuppressWarnings("unchecked")
            QuantityModel<?> converted = ((QuantityModel<IMeasurable>) model).to(targetUnitEnum);
            QuantityDTO result = convertToDTO(converted);
            
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity, "CONVERT", result);
            repository.save(entity);
            System.out.println("Conversion completed successfully: " + quantity + " -> " + result);
            return entity;
        } catch (Exception e) {
            System.err.println("Error during conversion: " + e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity, "CONVERT", e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    public QuantityMeasurementEntity addQuantities(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Adding quantities: " + quantity1 + " and " + quantity2);
        try {
            validateQuantities(quantity1, quantity2);
            QuantityModel<?> model1 = convertToModel(quantity1);
            QuantityModel<?> model2 = convertToModel(quantity2);
            
            @SuppressWarnings("unchecked")
            QuantityModel<?> result = ((QuantityModel<IMeasurable>) model1).add((QuantityModel<IMeasurable>) model2);
            QuantityDTO resultDTO = convertToDTO(result);
            
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "ADD", resultDTO);
            repository.save(entity);
            System.out.println("Addition completed successfully: " + quantity1 + " + " + quantity2 + " = " + resultDTO);
            return entity;
        } catch (Exception e) {
            System.err.println("Error during addition: " + e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "ADD", e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    public QuantityMeasurementEntity subtractQuantities(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Subtracting quantities: " + quantity1 + " and " + quantity2);
        try {
            validateQuantities(quantity1, quantity2);
            QuantityModel<?> model1 = convertToModel(quantity1);
            QuantityModel<?> model2 = convertToModel(quantity2);
            
            @SuppressWarnings("unchecked")
            QuantityModel<?> result = ((QuantityModel<IMeasurable>) model1).subtract((QuantityModel<IMeasurable>) model2);
            QuantityDTO resultDTO = convertToDTO(result);
            
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "SUBTRACT", resultDTO);
            repository.save(entity);
            System.out.println("Subtraction completed successfully: " + quantity1 + " - " + quantity2 + " = " + resultDTO);
            return entity;
        } catch (Exception e) {
            System.err.println("Error during subtraction: " + e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "SUBTRACT", e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    @Override
    public QuantityMeasurementEntity divideQuantities(QuantityDTO quantity1, QuantityDTO quantity2) {
        System.out.println("Dividing quantities: " + quantity1 + " and " + quantity2);
        try {
            validateQuantities(quantity1, quantity2);
            QuantityModel<?> model1 = convertToModel(quantity1);
            QuantityModel<?> model2 = convertToModel(quantity2);
            
            @SuppressWarnings("unchecked")
            double result = ((QuantityModel<IMeasurable>) model1).divide((QuantityModel<IMeasurable>) model2);
            QuantityDTO resultDTO = new QuantityDTO(result, "SCALAR", "DIMENSIONLESS");
            
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "DIVIDE", resultDTO);
            repository.save(entity);
            System.out.println("Division completed successfully: " + quantity1 + " ÷ " + quantity2 + " = " + resultDTO);
            return entity;
        } catch (Exception e) {
            System.err.println("Error during division: " + e.getMessage());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(quantity1, quantity2, "DIVIDE", e.getMessage());
            repository.save(entity);
            return entity;
        }
    }

    private void validateQuantity(QuantityDTO quantity) {
        if (quantity == null) {
            throw new QuantityMeasurementException("Quantity cannot be null");
        }
        if (quantity.getUnit() == null || quantity.getUnit().trim().isEmpty()) {
            throw new QuantityMeasurementException("Unit cannot be null or empty");
        }
        if (quantity.getMeasurementType() == null || quantity.getMeasurementType().trim().isEmpty()) {
            throw new QuantityMeasurementException("Measurement type cannot be null or empty");
        }
    }

    private void validateQuantities(QuantityDTO quantity1, QuantityDTO quantity2) {
        validateQuantity(quantity1);
        validateQuantity(quantity2);
        
        if (!quantity1.getMeasurementType().equals(quantity2.getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot compare quantities of different measurement types");
        }
    }

    private QuantityModel<?> convertToModel(QuantityDTO dto) {
        IMeasurable unit = getUnitFromName(dto.getUnit(), dto.getMeasurementType());
        return new QuantityModel<>(dto.getValue(), unit);
    }

    private QuantityDTO convertToDTO(QuantityModel<?> model) {
        return new QuantityDTO(model.getValue(), model.getUnit().getUnitName(), 
                             model.getUnit().getClass().getSimpleName().replace("Unit", "").toUpperCase());
    }

    private IMeasurable getUnitFromName(String unitName, String measurementType) {
        switch (measurementType) {
            case "LENGTH":
                return LengthUnit.valueOf(unitName);
            case "WEIGHT":
                return WeightUnit.valueOf(unitName);
            case "VOLUME":
                return VolumeUnit.valueOf(unitName);
            case "TEMPERATURE":
                return TemperatureUnit.valueOf(unitName);
            default:
                throw new QuantityMeasurementException("Unknown measurement type: " + measurementType);
        }
    }
}
