package com.example.sri.ArtemRomanenkoS32237.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;
    private Double pricePerDay;

    @OneToMany(mappedBy = "parking")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Car> cars;

}
