package com.example.sri.ArtemRomanenkoS32237.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDetailsDto extends RepresentationModel<ParkingDetailsDto> {

    private Long id;

    @Size(min = 1, max = 48, message = "address value should be 1to48 characters long")
    private String address;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Price cannot have more than two decimal places")
    private Double pricePerDay;

    private Set<CarDto> cars;

}
