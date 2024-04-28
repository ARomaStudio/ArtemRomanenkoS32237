package com.example.sri.ArtemRomanenkoS32237.service;

import com.example.sri.ArtemRomanenkoS32237.config.exceptions.ResourceNotFoundException;
import com.example.sri.ArtemRomanenkoS32237.model.Car;
import com.example.sri.ArtemRomanenkoS32237.model.Parking;
import com.example.sri.ArtemRomanenkoS32237.repo.CarRepository;
import com.example.sri.ArtemRomanenkoS32237.repo.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private static final String PARKING_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE = "Parking resource with id: %s, not found in database";
    private static final String CAR_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE = "Car resource with id: %s, not found in database";

    private final ParkingRepository parkingRepository;
    private final CarRepository carRepository;

    public List<Parking> getAll() {
        return parkingRepository.findAll();
    }

    public Parking create(Parking parking) {
        return parkingRepository.save(parking);
    }

    public Optional<Parking> update(Long id, Parking parking) {
        Optional<Parking> carFromDb = parkingRepository.findById(id);
        if (carFromDb.isPresent()) {
            parking.setId(id);
            parkingRepository.save(parking);
        }
        return carFromDb;
    }

    public Parking getById(Long id) {
        Optional<Parking> parkingFromDb =  parkingRepository.findDetailsById(id);
        if (parkingFromDb.isEmpty()) {
            throw new ResourceNotFoundException(PARKING_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(id));
        }
        return parkingFromDb.get();
    }

    public void deleteById(Long id) {
        boolean exists = parkingRepository.existsById(id);
        if (exists) {
            parkingRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(PARKING_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(id));
        }
    }

    public void assignCarForParking(Long parkingId,
                                    Long carId) {
        Optional<Parking> parking = parkingRepository.findDetailsById(parkingId);
        Optional<Car> car = carRepository.findById(carId);
        if (parking.isEmpty()) {
            throw new ResourceNotFoundException(PARKING_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(parkingId));
        }
        if (car.isEmpty()) {
            throw new ResourceNotFoundException(CAR_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(carId));
        }
        car.get().setParking(parking.get());
        parking.get().getCars().add(car.get());
        carRepository.save(car.get());
    }

    public void removeCarForParking(Long parkingId,
                                    Long carId) {
        Optional<Parking> parking = parkingRepository.findDetailsById(parkingId);
        Optional<Car> car = carRepository.findById(carId);
        if (parking.isEmpty()) {
            throw new ResourceNotFoundException(PARKING_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(parkingId));
        }
        if (car.isEmpty()) {
            throw new ResourceNotFoundException(CAR_RESOURCE_NOT_FOUND_MESSAGE_TEMPLATE.formatted(carId));
        }
        car.get().setParking(null);
        parking.get().getCars().remove(car.get());
        carRepository.save(car.get());
    }

}
