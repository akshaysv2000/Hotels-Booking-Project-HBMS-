package com.example.hbmSystem.controllers;

import com.example.hbmSystem.Security.CustomHotelDetails;
import com.example.hbmSystem.dto.AmountPerDayDTO;
import com.example.hbmSystem.dto.BookingsPerDayDTO;
import com.example.hbmSystem.dto.BookingsPerRoomTypeDTO;
import com.example.hbmSystem.dto.DashboardStatsDTO;
import com.example.hbmSystem.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotel/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStatsDTO getStats(@AuthenticationPrincipal CustomHotelDetails details) {
        return dashboardService.getDashboardStats(details.getHotelId());
    }
    @GetMapping("/bookings-per-day")
    public List<BookingsPerDayDTO> getBookingsPerDay(@AuthenticationPrincipal CustomHotelDetails details) {
        return dashboardService.getBookingsPerDay(details.getHotelId());
    }

    @GetMapping("/amount-per-day")
    public List<AmountPerDayDTO> getAmountPerDay(@AuthenticationPrincipal CustomHotelDetails details) {
        return dashboardService.getAmountPerDay(details.getHotelId());
    }

    @GetMapping("/bookings-per-roomtype")
    public List<BookingsPerRoomTypeDTO> getBookingsPerRoomType(@AuthenticationPrincipal CustomHotelDetails details) {
        return dashboardService.getBookingsPerRoomType(details.getHotelId());
    }


}
