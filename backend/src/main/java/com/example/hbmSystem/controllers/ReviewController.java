package com.example.hbmSystem.controllers;

import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.models.Review;
import com.example.hbmSystem.models.User;
import com.example.hbmSystem.repository.HotelRepository;
import com.example.hbmSystem.repository.UserRepository;
import com.example.hbmSystem.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/hotels")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @PostMapping("/user/{hotelId}/reviews")
    public ResponseEntity<Map<String, String>> addReview(@PathVariable int hotelId, @RequestBody Review reviewInput, Authentication authentication ) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));
        Review savedReview = reviewService.addReview(user, hotel, reviewInput.getRating(), reviewInput.getComment());
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Review saved successfully"));

    }
    @GetMapping("/{hotelId}/reviews")
    public ResponseEntity<List<Review>> getReviews(@PathVariable int hotelId) {
        List<Review> reviews = reviewService.getReviewsForHotel(hotelId);
        return ResponseEntity.ok(reviews);
    }
}
