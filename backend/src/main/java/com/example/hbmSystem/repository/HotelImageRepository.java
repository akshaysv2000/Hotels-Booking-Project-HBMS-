package com.example.hbmSystem.repository;


import com.example.hbmSystem.models.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Integer> {
    List<HotelImage> findByHotel_HotelId(int hotelId);
}

