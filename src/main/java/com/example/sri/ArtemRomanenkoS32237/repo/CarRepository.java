package com.example.sri.ArtemRomanenkoS32237.repo;

import com.example.sri.ArtemRomanenkoS32237.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findAll();
    Optional<Car> findById(Long id);
}
