package com.example.hbmSystem.repository;

import com.example.hbmSystem.models.HotelDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends JpaRepository<HotelDescription,Integer> {
}
