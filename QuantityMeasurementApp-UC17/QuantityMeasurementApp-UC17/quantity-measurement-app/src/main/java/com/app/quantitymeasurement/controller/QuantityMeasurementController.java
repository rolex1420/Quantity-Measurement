package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.model.OperationType;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService quantityMeasurementService;

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(@RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = quantityMeasurementService.compareQuantities(input);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert quantity from one unit to another")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(@RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = quantityMeasurementService.convertQuantity(input);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(@RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = quantityMeasurementService.addQuantities(input);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(@RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = quantityMeasurementService.subtractQuantities(input);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/multiply")
    @Operation(summary = "Multiply two quantities")
    public ResponseEntity<QuantityMeasurementDTO> multiplyQuantities(@RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = quantityMeasurementService.multiplyQuantities(input);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(@RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = quantityMeasurementService.divideQuantities(input);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get operation history by operation type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable OperationType operation) {
        List<QuantityMeasurementDTO> history = quantityMeasurementService.getOperationHistory(operation);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get measurements by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMeasurementsByType(@PathVariable String measurementType) {
        List<QuantityMeasurementDTO> measurements = quantityMeasurementService.getMeasurementsByType(measurementType);
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get operation count by operation type")
    public ResponseEntity<Long> getOperationCount(@PathVariable OperationType operation) {
        long count = quantityMeasurementService.getOperationCount(operation);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get error history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        List<QuantityMeasurementDTO> errors = quantityMeasurementService.getErrorHistory();
        return ResponseEntity.ok(errors);
    }
}
