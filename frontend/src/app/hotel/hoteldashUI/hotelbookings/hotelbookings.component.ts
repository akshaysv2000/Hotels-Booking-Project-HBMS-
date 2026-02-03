import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

interface HotelBookingListDTO {
  bookingDate: string;
  roomType: string;
  numberOfRooms: string;
   check_in_date: string;
  check_out_date: string;
  amount: string;
  paymentStatus: string;
  customerName: string;
  customerPhone: string;
}

@Component({
  selector: 'app-hotelbookings',
  templateUrl: './hotelbookings.component.html',
  styleUrls: ['./hotelbookings.component.css']
})
export class HotelbookingsComponent implements OnInit {
  bookings: HotelBookingListDTO[] = [];
  loading = true;
  error: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchBookings();
  }

  fetchBookings() {
    this.http.get<HotelBookingListDTO[]>('http://localhost:8080/hotel/bookingList').subscribe({
      next: (data) => {
        this.bookings = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Could not load bookings.';
        this.loading = false;
      }
    });
  }
}