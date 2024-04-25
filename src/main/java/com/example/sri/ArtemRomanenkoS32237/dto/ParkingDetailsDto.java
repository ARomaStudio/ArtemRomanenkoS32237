package com.example.sri.ArtemRomanenkoS32237.dto;

import com.example.sri.ArtemRomanenkoS32237.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDetailsDto {

    private Long id;

    private String address;

    private Double pricePerDay;

    private Set<CarDto> cars;

}
