package com.example.sri.ArtemRomanenkoS32237.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 1, max = 48, message = "address value should be 1to48 characters long")
    private String address;

    @Column(columnDefinition = "NUMERIC(19, 2)", nullable = false)
    @DecimalMin(value = "0", message = "Price must be positive")
    private BigDecimal pricePerDay;

    @OneToMany(mappedBy = "parking")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Car> cars;

}
