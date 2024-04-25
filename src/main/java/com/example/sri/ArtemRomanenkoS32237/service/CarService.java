package com.example.sri.ArtemRomanenkoS32237.service;

import com.example.sri.ArtemRomanenkoS32237.model.Car;
import com.example.sri.ArtemRomanenkoS32237.repo.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car create(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> update(Long id, Car car) {
        Optional<Car> carFromDb = carRepository.findById(id);
        if (carFromDb.isPresent()) {
            car.setId(id);
            carRepository.save(car);
        }
        return carFromDb;
    }

    public Optional<Car> getById(Long id) {
        return carRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        boolean exists = carRepository.existsById(id);
        if (exists) carRepository.deleteById(id);
        return exists;
    }

}
