package com.example.hbmSystem.repository;

import com.example.hbmSystem.models.HotelFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelFacilityRepository extends JpaRepository<HotelFacility, Integer> {

    List<HotelFacility> findByHotel_HotelId(int hotelId);

}
