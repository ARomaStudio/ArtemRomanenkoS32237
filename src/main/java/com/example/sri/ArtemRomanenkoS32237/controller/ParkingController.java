package com.example.sri.ArtemRomanenkoS32237.controller;

import com.example.sri.ArtemRomanenkoS32237.dto.CarDto;
import com.example.sri.ArtemRomanenkoS32237.dto.ParkingDetailsDto;
import com.example.sri.ArtemRomanenkoS32237.dto.ParkingDto;
import com.example.sri.ArtemRomanenkoS32237.model.Parking;
import com.example.sri.ArtemRomanenkoS32237.service.ParkingService;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/parkings")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final ModelMapper modelMapper;

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<CollectionModel<ParkingDto>> getAll() {
        List<ParkingDto> result = parkingService.getAll()
                .stream()
                .map(e -> modelMapper.map(e, ParkingDto.class))
                .map(e -> e.add(linkTo(methodOn(ParkingController.class).getParkingById(e.getId())).withSelfRel()))
                .collect(Collectors.toList());
        CollectionModel<ParkingDto> model = CollectionModel.of(result, linkTo(methodOn(ParkingController.class).getAll()).withSelfRel());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Collection<ParkingDto>> createParking(@Valid @RequestBody ParkingDto parkingDto) {
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

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<ParkingDetailsDto> getParkingById(@PathVariable Long id) {
        Parking parking = parkingService.getById(id);

        ParkingDetailsDto parkingDetailsDto = modelMapper.map(parking, ParkingDetailsDto.class);
        parkingDetailsDto.add(linkTo(methodOn(ParkingController.class).getParkingById(id)).withSelfRel());
        parkingDetailsDto.add(linkTo(methodOn(ParkingController.class).getRelatedCars(id)).withSelfRel());
        parkingDetailsDto.setCars(parkingDetailsDto.getCars().stream()
                    .map(e -> e.add(linkTo(methodOn(CarController.class).getCarById(e.getId())).withSelfRel()))
                    .collect(Collectors.toSet()));
        return new ResponseEntity<>(parkingDetailsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/cars", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<CarDto>> getRelatedCars(@PathVariable Long id) {
        Parking parking = parkingService.getById(id);
        List<CarDto> carDtos = parking.getCars().stream()
                    .map(e -> modelMapper.map(e, CarDto.class))
                    .map(e -> e.add(linkTo(methodOn(CarController.class).getCarById(e.getId())).withSelfRel()))
                    .toList();
        CollectionModel<CarDto> model = CollectionModel.of(carDtos, linkTo(methodOn(ParkingController.class).getRelatedCars(id)).withSelfRel());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @PutMapping("/{parkingId}/cars/{carId}")
    public ResponseEntity<ParkingDto> assignCarForParking(@PathVariable("parkingId") Long parkingId,
                                                          @PathVariable("carId") Long carId) {
        parkingService.assignCarForParking(parkingId, carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{parkingId}/cars/{carId}")
    public ResponseEntity<ParkingDto> removeCarForParking(@PathVariable("parkingId") Long parkingId,
                                                          @PathVariable("carId") Long carId) {
        parkingService.removeCarForParking(parkingId, carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDto> updateParking(@PathVariable Long id,
                                                    @Valid @RequestBody ParkingDto carDto) {
        Optional<Parking> updatedParking = parkingService.update(id, modelMapper.map(carDto, Parking.class));
        if(updatedParking.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingDto> deleteParking(@PathVariable Long id) {
        parkingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
