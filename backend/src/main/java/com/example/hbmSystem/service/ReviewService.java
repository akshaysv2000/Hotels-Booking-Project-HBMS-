package com.example.hbmSystem.service;

import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.models.Review;
import com.example.hbmSystem.models.User;
import com.example.hbmSystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    public Review addReview(User user, Hotel hotel, int rating, String comment) {
        Review review = new Review(user, hotel, rating, comment);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForHotel(int hotelId) {
        return reviewRepository.findByHotel_HotelIdOrderByReviewDateDesc(hotelId);
    }
}

