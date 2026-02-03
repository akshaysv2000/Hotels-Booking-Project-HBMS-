import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HotelServiceService {
constructor(private http: HttpClient) {}

  checkHotelDetailsCompletion(): Observable<boolean> {
    return this.http.get<boolean>('http://localhost:8080/hotel/status');
  }
}
