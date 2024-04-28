package com.example.sri.ArtemRomanenkoS32237.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto extends RepresentationModel<CarDto> {

    private Long id;

    @Size(min=1, max=48, message = "modelName value should be 1to48 characters long")
    private String modelName;
    @Size(min=1, max=48, message = "manufacturerName value should be 1to48 characters long")
    private String manufacturerName;
    @NotNull(message = "manufactureDate cant be null")
    @Past(message = "manufactureDate should be some date in the past")
    private LocalDate manufactureDate;
    @Size(min=1, max=48, message = "color value should be 1to48 characters long")
    private String color;

}

