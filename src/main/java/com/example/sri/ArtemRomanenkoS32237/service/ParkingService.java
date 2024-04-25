package com.example.sri.ArtemRomanenkoS32237.service;

import com.example.sri.ArtemRomanenkoS32237.model.Parking;
import com.example.sri.ArtemRomanenkoS32237.repo.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;

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

    public Optional<Parking> getById(Long id) {
        return parkingRepository.findDetailsById(id);
    }

    public boolean deleteById(Long id) {
        boolean exists = parkingRepository.existsById(id);
        if (exists) parkingRepository.deleteById(id);
        return exists;
    }

}
