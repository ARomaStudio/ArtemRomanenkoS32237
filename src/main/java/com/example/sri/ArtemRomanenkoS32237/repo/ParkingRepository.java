package com.example.sri.ArtemRomanenkoS32237.repo;

import com.example.sri.ArtemRomanenkoS32237.model.Parking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends CrudRepository<Parking, Long> {
    List<Parking> findAll();
    Optional<Parking> findById(Long id);

    @Query("from Parking as p left join fetch p.cars where p.id =:parkingId")
    Optional<Parking> findDetailsById(@Param("parkingId") Long id);

}
