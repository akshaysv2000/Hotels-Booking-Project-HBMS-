package com.example.hbmSystem.controllers;

import com.example.hbmSystem.Security.CustomUserDetails;
import com.example.hbmSystem.dto.BookingDTO;
import com.example.hbmSystem.dto.BookingRequest;
import com.example.hbmSystem.models.Booking;
import com.example.hbmSystem.service.BookingService;
import com.example.hbmSystem.service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private RazorpayService razorpayService;

    @GetMapping("/user/check-availability")
    public ResponseEntity<?> checkAvailability(
            @RequestParam int roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam int numberOfRooms
    ) {
        try {
            boolean available = bookingService.checkRoomAvailability(roomId, checkIn, checkOut, numberOfRooms);
            BigDecimal totalPrice = bookingService.calculateTotalPrice(roomId, checkIn, checkOut, numberOfRooms);

            Map<String, Object> response = Map.of("available", available, "totalPrice", totalPrice);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking availability: " + e.getMessage());
        }
    }




    @PostMapping("/user/book")
    public ResponseEntity<?> createBookingWithPayment(@RequestBody BookingRequest bookingRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            int userId = userDetails.getUserId();

            Booking booking = bookingService.createBooking(
                    userId,
                    bookingRequest.getHotelId(),
                    bookingRequest.getRoomId(),
                    bookingRequest.getCheckInDate(),
                    bookingRequest.getCheckOutDate(),
                    bookingRequest.getNumberOfRooms()
            );

            BigDecimal totalPrice = bookingService.calculateTotalPrice(
                    bookingRequest.getRoomId(),
                    bookingRequest.getCheckInDate(),
                    bookingRequest.getCheckOutDate(),
                    bookingRequest.getNumberOfRooms()
            );

            String razorpayOrderId = razorpayService.createRazorpayOrder(totalPrice);

            razorpayService.createPaymentForBooking(booking, totalPrice, razorpayOrderId);

            return ResponseEntity.ok(Map.of(
                    "booking", bookingService.convertToDto(booking),
                    "razorpayOrderId", razorpayOrderId,
                    "amount", totalPrice.multiply(BigDecimal.valueOf(100)).intValue() // amount in paise
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create booking with payment: " + ex.getMessage());
        }
    }

    @PostMapping("/user/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentDetails) {
        try {
            boolean valid = razorpayService.verifyPaymentSignature(paymentDetails);
            if (!valid) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment signature");
            }
            razorpayService.updatePaymentStatus(paymentDetails);
            return ResponseEntity.ok(Map.of("text", "Payment verified successfully"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid payment signature"));
        }
    }
}
