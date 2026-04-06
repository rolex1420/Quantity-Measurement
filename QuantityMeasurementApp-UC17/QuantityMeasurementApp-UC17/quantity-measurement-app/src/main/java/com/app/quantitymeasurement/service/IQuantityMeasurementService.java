package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.model.OperationType;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compareQuantities(QuantityInputDTO input);

    QuantityMeasurementDTO convertQuantity(QuantityInputDTO input);

    QuantityMeasurementDTO addQuantities(QuantityInputDTO input);

    QuantityMeasurementDTO subtractQuantities(QuantityInputDTO input);

    QuantityMeasurementDTO multiplyQuantities(QuantityInputDTO input);

    QuantityMeasurementDTO divideQuantities(QuantityInputDTO input);

    List<QuantityMeasurementDTO> getOperationHistory(OperationType operation);

    List<QuantityMeasurementDTO> getMeasurementsByType(String measurementType);

    long getOperationCount(OperationType operation);

    List<QuantityMeasurementDTO> getErrorHistory();
}
