package com.example.hbmSystem.repository;

import com.example.hbmSystem.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByHotel_HotelIdOrderByReviewDateDesc(int hotelId);

}
