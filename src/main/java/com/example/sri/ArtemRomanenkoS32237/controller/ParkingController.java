package com.example.sri.ArtemRomanenkoS32237.controller;

import com.example.sri.ArtemRomanenkoS32237.dto.CarDto;
import com.example.sri.ArtemRomanenkoS32237.dto.ParkingDetailsDto;
import com.example.sri.ArtemRomanenkoS32237.dto.ParkingDto;
import com.example.sri.ArtemRomanenkoS32237.model.Parking;
import com.example.sri.ArtemRomanenkoS32237.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Collection<ParkingDto>> getAll() {
        List<ParkingDto> result = parkingService.getAll()
                .stream()
                .map(e -> modelMapper.map(e, ParkingDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Collection<ParkingDto>> createCar(@RequestBody ParkingDto parkingDto) {
        Parking parking = modelMapper.map(parkingDto, Parking.class);
        parkingService.create(parking);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(parking.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> getCarById(@PathVariable Long id) {
        Optional<Parking> parking = parkingService.getById(id);
        if(parking.isPresent()) {
            return new ResponseEntity<>(modelMapper.map(parking.get(), ParkingDetailsDto.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/cars")
    public ResponseEntity<Collection<CarDto>> getRelatedCars(@PathVariable Long id) {
        Optional<Parking> parking = parkingService.getById(id);
        if(parking.isPresent()) {
            return new ResponseEntity<>(modelMapper.map(parking.get(), ParkingDto.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{parkingId}/cars/{carId}")
    public ResponseEntity assignCarForParking(@PathVariable("parkingId") String parkingId, @PathVariable("carId") String CarId) {
        return new ResponseEntity();
    }

    @PutMapping("/{parkingId}/cars/{carId}")
    public ResponseEntity removeCarForParking(@PathVariable("parkingId") String parkingId, @PathVariable("carId") String CarId) {
        return new ResponseEntity();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDto> updateCar(@PathVariable Long id,
                                            @RequestBody ParkingDto carDto) {
        Optional<Parking> updatedParking = parkingService.update(id, modelMapper.map(carDto, Parking.class));
        if(updatedParking.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingDto> deleteCar(@PathVariable Long id) {
        if(parkingService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
