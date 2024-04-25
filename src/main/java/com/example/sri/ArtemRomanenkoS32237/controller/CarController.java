package com.example.sri.ArtemRomanenkoS32237.controller;

import com.example.sri.ArtemRomanenkoS32237.dto.CarDto;
import com.example.sri.ArtemRomanenkoS32237.model.Car;
import com.example.sri.ArtemRomanenkoS32237.service.CarService;
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
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Collection<CarDto>> getAllCars() {
        List<CarDto> result = carService.getAll()
                .stream()
                .map(e -> modelMapper.map(e, CarDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Collection<CarDto>> createCar(@RequestBody CarDto carDto) {
        Car car = modelMapper.map(carDto, Car.class);
        carService.create(car);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(car.getId())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getById(id);
        if(car.isPresent()) {
            return new ResponseEntity<>(modelMapper.map(car.get(), CarDto.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id,
                                            @RequestBody CarDto carDto) {
        Optional<Car> updatedCar = carService.update(id, modelMapper.map(carDto, Car.class));
        if(updatedCar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable Long id) {
        if(carService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
