package com.example.demo.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


// Step 1 - Add more robust validation
public record ProductRecordDto(
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @NotNull(message = "Price cannot be null")
    @Min(2)
    @Max(100000)
    BigDecimal value,

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 2, max = 255, message = "Description must be between 2 and 255 characters")
    String description) {
}
