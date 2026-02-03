import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HotelCardSearchResultDTO } from './searchresult/models/hotel';

@Injectable({
  providedIn: 'root'
})
export class HotelServiceService {

  private baseUrl = 'http://localhost:8080/user/hotels/search';  // Adjust backend base URL accordingly

  constructor(private http: HttpClient) {}

  searchHotels(
    name: string,
    latitude?: number,
    longitude?: number,
    location?: string
  ): Observable<HotelCardSearchResultDTO> {
    let params = new HttpParams().set('name', name);

    if (latitude != null && longitude != null) {
      params = params.set('latitude', latitude.toString()).set('longitude', longitude.toString());
    }
    if (location) {
      params = params.set('location', location);
    }

    return this.http.get<HotelCardSearchResultDTO>(this.baseUrl, { params });
  }
}
