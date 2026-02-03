package com.example.hbmSystem.dto;

import java.time.LocalDate;

public class DashboardStatsDTO {
    private long totalBookings;
    private double totalAmountReceived;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(long totalBookings, double totalAmountReceived) {
        this.totalBookings = totalBookings;
        this.totalAmountReceived = totalAmountReceived;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public double getTotalAmountReceived() {
        return totalAmountReceived;
    }

    public void setTotalAmountReceived(double totalAmountReceived) {
        this.totalAmountReceived = totalAmountReceived;
    }
}

