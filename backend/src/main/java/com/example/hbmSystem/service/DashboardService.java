package com.example.hbmSystem.service;

import com.example.hbmSystem.dto.AmountPerDayDTO;
import com.example.hbmSystem.dto.BookingsPerDayDTO;
import com.example.hbmSystem.dto.BookingsPerRoomTypeDTO;
import com.example.hbmSystem.dto.DashboardStatsDTO;
import com.example.hbmSystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private BookingRepository bookingRepository;

    public DashboardStatsDTO getDashboardStats(int hotelId) {
        long total = bookingRepository.countByHotelId(hotelId);
        Double amount = bookingRepository.sumAmountByHotelId(hotelId);
        return new DashboardStatsDTO(total, amount != null ? amount : 0);
    }

    public List<BookingsPerDayDTO> getBookingsPerDay(int hotelId) {
        return bookingRepository.bookingsPerDay(hotelId).stream()
                .map(r -> new BookingsPerDayDTO(
                        ((java.sql.Date) r[0]).toLocalDate(),
                        (Long) r[1]
                ))
                .toList();
    }


    public List<AmountPerDayDTO> getAmountPerDay(int hotelId) {
        List<Object[]> results = bookingRepository.amountPerDay(hotelId);
        return results.stream()
                .map(r -> new AmountPerDayDTO(
                        ((java.sql.Date) r[0]).toLocalDate(),   // Date extracted from SQL result
                        r[1] != null ? ((Number) r[1]).doubleValue() : 0.0
                ))
                .toList();
    }

    public List<BookingsPerRoomTypeDTO> getBookingsPerRoomType(int hotelId) {
        List<Object[]> results = bookingRepository.bookingsPerRoomType(hotelId);
        return results.stream()
                .map(r -> new BookingsPerRoomTypeDTO(
                        (String) r[0],
                        r[1] != null ? ((Number) r[1]).longValue() : 0L
                ))
                .toList();
    }

}
