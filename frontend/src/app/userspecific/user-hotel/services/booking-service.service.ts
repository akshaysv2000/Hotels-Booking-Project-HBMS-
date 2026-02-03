import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface AvailabilityResponse {
  available: boolean;
  totalPrice: number;
}



@Injectable({
  providedIn: 'root'
})
export class BookingServiceService {
 private baseUrl = 'http://localhost:8080/user'; // Adjust base URL as needed

  constructor(private http: HttpClient) {}

  checkAvailability(roomId: number, checkIn: string, checkOut: string, numberOfRooms: number): Observable<AvailabilityResponse> {
    const params = new HttpParams()
      .set('roomId', roomId.toString())
      .set('checkIn', checkIn)
      .set('checkOut', checkOut)
      .set('numberOfRooms', numberOfRooms.toString());

    return this.http.get<AvailabilityResponse>(`${this.baseUrl}/check-availability`, { params });
  }

  // Add other methods like booking creation here
   createBookingWithPayment(bookingData: any) {
    return this.http.post(`${this.baseUrl}/book`, bookingData);
  }

  verifyPayment(paymentDetails: any) {
    return this.http.post(`${this.baseUrl}/verify-payment`, paymentDetails);
  }
}
