package com.example.hbmSystem.dto;

import java.time.LocalDate;

public class BookingsPerDayDTO {

        private LocalDate date;
        private long bookingsCount;

    public BookingsPerDayDTO() {
    }

    public BookingsPerDayDTO(LocalDate date, long bookingsCount) {
        this.date = date;
        this.bookingsCount = bookingsCount;
    }

    public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public long getBookingsCount() {
            return bookingsCount;
        }

        public void setBookingsCount(long bookingsCount) {
            this.bookingsCount = bookingsCount;
        }

}
