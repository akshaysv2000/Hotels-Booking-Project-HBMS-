package com.example.hbmSystem.repository;

import com.example.hbmSystem.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {
    Optional<Hotel> findByUsername(String username);
    List<Hotel> findByStatus(Hotel.Status status);
    List<Hotel> findByNameContainingIgnoreCaseAndStatus(String name, Hotel.Status status);
    boolean existsByUsername(String username);
    List<Hotel> findByLocationContainingIgnoreCaseAndStatus(String location, Hotel.Status status);



    @Query("SELECT h FROM Hotel h WHERE h.status = 'Approved' AND (6371 * acos(cos(radians(:lat)) * cos(radians(h.latitude)) * cos(radians(h.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(h.latitude)))) < :radius")
    List<Hotel> findWithinRadius(@Param("lat") Double lat, @Param("lng") Double lng, @Param("radius") Double radiusKm);



    long countByStatus(Hotel.Status status);


}
