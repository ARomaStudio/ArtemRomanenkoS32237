package com.example.sri.ArtemRomanenkoS32237.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDto {

    private Long id;

    private String address;
    private Double pricePerDay;

}
