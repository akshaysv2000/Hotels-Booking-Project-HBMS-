import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HotelDashboardService {

   private baseUrl = 'http://localhost:8080/hotel/dashboard';

  constructor(private http: HttpClient) {}

  getStats() {
    return this.http.get<any>(`${this.baseUrl}/stats`);
  }

  getBookingsPerDay() {
    return this.http.get<any[]>(`${this.baseUrl}/bookings-per-day`);
  }

  getAmountPerDay() {
    return this.http.get<any[]>(`${this.baseUrl}/amount-per-day`);
  }

  getBookingsPerRoomType() {
    return this.http.get<any[]>(`${this.baseUrl}/bookings-per-roomtype`);
  }
}
