package com.example.hbmSystem.dto;

public class ReviewDTO {
    private String reviewDate;
    private String comment;
    private int rating;

    public ReviewDTO() {
    }

    public ReviewDTO(String reviewDate, String comment, int rating) {
        this.reviewDate = reviewDate;
        this.comment = comment;
        this.rating = rating;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "reviewDate='" + reviewDate + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
