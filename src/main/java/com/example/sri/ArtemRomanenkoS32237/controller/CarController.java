package com.example.sri.ArtemRomanenkoS32237.controller;

import com.example.sri.ArtemRomanenkoS32237.dto.CarDto;
import com.example.sri.ArtemRomanenkoS32237.model.Car;
import com.example.sri.ArtemRomanenkoS32237.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<CollectionModel<CarDto>> getAllCars() {
        List<CarDto> result = carService.getAll()
                .stream()
                .map(e -> modelMapper.map(e, CarDto.class))
                .map(e -> e.add(linkTo(methodOn(CarController.class).getCarById(e.getId())).withSelfRel()))
                .collect(Collectors.toList());
        CollectionModel<CarDto> model = CollectionModel.of(result, linkTo(methodOn(CarController.class).getAllCars()).withSelfRel());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Collection<CarDto>> createCar(@Valid @RequestBody CarDto carDto) {
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

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        Car car = carService.getById(id);
        CarDto carDto = modelMapper.map(car, CarDto.class);
        carDto.add(linkTo(methodOn(CarController.class).getCarById(id)).withSelfRel());
        return new ResponseEntity<>(carDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id,
                                            @Valid @RequestBody CarDto carDto) {
        carService.update(id, modelMapper.map(carDto, Car.class));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
