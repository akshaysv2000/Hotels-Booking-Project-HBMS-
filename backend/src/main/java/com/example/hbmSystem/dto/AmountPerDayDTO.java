package com.example.hbmSystem.dto;

import java.time.LocalDate;

public class AmountPerDayDTO {
    private LocalDate date;
    private double totalAmount;

    public AmountPerDayDTO() {
    }

    public AmountPerDayDTO(LocalDate date, double totalAmount) {
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
