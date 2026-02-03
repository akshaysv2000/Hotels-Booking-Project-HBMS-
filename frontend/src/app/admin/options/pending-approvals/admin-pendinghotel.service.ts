import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface HotelDTO {
  hotelId: number;
  name: string;
  address: string;
  ownerName: string;
  contactNumber: string;
  status: string;
  createdAt: string;
}


@Injectable({
  providedIn: 'root'
})
export class AdminPendinghotelService {
 private baseUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) {}

  getPendingHotels(): Observable<HotelDTO[]> {
    return this.http.get<HotelDTO[]>(`${this.baseUrl}/hotels/pending`);
  }

  approveHotel(hotelId: number): Observable<any> {
    return this.http.patch(`${this.baseUrl}/photel/${hotelId}/approve`, {});
  }

  rejectHotel(hotelId: number): Observable<any> {
    return this.http.patch(`${this.baseUrl}/photel/${hotelId}/reject`, {});
  }
}
