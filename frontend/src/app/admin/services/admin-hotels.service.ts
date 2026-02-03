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
  createdAt: string; // ISO date in string format
}

@Injectable({
  providedIn: 'root'
})
export class AdminHotelsService {
     private baseUrl = 'http://localhost:8080/admin/hotels/approved';

  constructor(private http: HttpClient) {}

  getApprovedHotels(): Observable<HotelDTO[]> {
    return this.http.get<HotelDTO[]>(this.baseUrl);
  }
}
