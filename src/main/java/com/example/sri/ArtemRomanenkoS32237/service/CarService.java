package com.example.sri.ArtemRomanenkoS32237.service;

import com.example.sri.ArtemRomanenkoS32237.config.exceptions.ResourceNotFoundException;
import com.example.sri.ArtemRomanenkoS32237.model.Car;
import com.example.sri.ArtemRomanenkoS32237.repo.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private static final String RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE = "Car resource with id: %s, not found in database";

    private final CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car create(Car car) {
        return carRepository.save(car);
    }

    public void update(Long id, Car car) {
        Optional<Car> carFromDb = carRepository.findById(id);
        if (carFromDb.isEmpty()) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(id));
        }
        car.setId(id);
        carRepository.save(car);
    }

    public Car getById(Long id) {
        Optional<Car> carFromDb = carRepository.findById(id);
        if (carFromDb.isEmpty()) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(id));
        }
        return carFromDb.get();
    }

    public void deleteById(Long id) {
        boolean exists = carRepository.existsById(id);
        if (exists) {
            carRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(id));
        }
    }

}
