package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {
    QuantityMeasurementEntity compareQuantities(QuantityDTO quantity1, QuantityDTO quantity2);
    QuantityMeasurementEntity convertQuantity(QuantityDTO quantity, String targetUnit);
    QuantityMeasurementEntity addQuantities(QuantityDTO quantity1, QuantityDTO quantity2);
    QuantityMeasurementEntity subtractQuantities(QuantityDTO quantity1, QuantityDTO quantity2);
    QuantityMeasurementEntity divideQuantities(QuantityDTO quantity1, QuantityDTO quantity2);
}
