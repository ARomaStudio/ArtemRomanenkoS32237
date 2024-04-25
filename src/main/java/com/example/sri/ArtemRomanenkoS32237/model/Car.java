package com.example.sri.ArtemRomanenkoS32237.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String modelName;
    private String manufacturerName;
    private LocalDate manufactureDate;
    private String color;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

}
