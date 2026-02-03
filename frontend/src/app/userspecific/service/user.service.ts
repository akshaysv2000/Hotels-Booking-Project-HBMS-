import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
private baseUrl = 'http://localhost:8080'; 

  constructor(private http: HttpClient) {}

  getUserProfile(): Observable<any> {
    return this.http.get(`${this.baseUrl}/user/profile`);
  }

  updateUserProfile(profile: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/user/profileUpdate`, profile);
  }

  getBookingList(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/user/bookingList`);
  }
}
