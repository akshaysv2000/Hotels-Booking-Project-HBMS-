package com.example.hbmSystem.controllers;

import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.repository.HotelRepository;
import com.example.hbmSystem.repository.PaymentRepository;
import com.example.hbmSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalApprovedHotels = hotelRepository.countByStatus(Hotel.Status.Approved);
        BigDecimal totalPaidAmount = paymentRepository.getTotalPaidAmount();


        if (totalPaidAmount == null) totalPaidAmount = BigDecimal.ZERO;

        return ResponseEntity.ok(
                Map.of(
                        "totalUsers", totalUsers,
                        "totalApprovedHotels", totalApprovedHotels,
                        "totalPaidAmount", totalPaidAmount
                )
        );
    }

}
