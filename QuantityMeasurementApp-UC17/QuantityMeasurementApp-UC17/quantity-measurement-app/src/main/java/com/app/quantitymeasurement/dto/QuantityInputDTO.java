package com.app.quantitymeasurement.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
public class QuantityInputDTO {

    @Valid
    @NotNull(message = "This quantity cannot be null")
    private QuantityDTO thisQuantityDTO;

    @Valid
    @NotNull(message = "That quantity cannot be null")
    private QuantityDTO thatQuantityDTO;
}
