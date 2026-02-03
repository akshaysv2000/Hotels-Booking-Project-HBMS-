import { Component, OnInit } from '@angular/core';
import { HotelCardSearchResultDTO } from './models/hotel';
import { ActivatedRoute } from '@angular/router';
import { HotelServiceService } from '../hotel-service.service';

@Component({
  selector: 'app-searchresult',
  templateUrl: './searchresult.component.html',
  styleUrls: ['./searchresult.component.css']
})
export class SearchresultComponent implements OnInit { query: string = '';
  latitude?: number;
  longitude?: number;
  location?: string;

  results: HotelCardSearchResultDTO | null = null;
  loading: boolean = false;
  error: string | null = null;

  constructor(private route: ActivatedRoute, private hotelService: HotelServiceService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.query = params['name'] || '';
      this.location = params['location'] || undefined;
      this.latitude = params['latitude'] ? +params['latitude'] : undefined;
      this.longitude = params['longitude'] ? +params['longitude'] : undefined;

      if (this.query) {
        this.fetchHotels();
      }
    });
  }



  fetchHotels(): void {
    this.loading = true;
    this.error = null;

    this.hotelService.searchHotels(this.query, this.latitude, this.longitude, this.location)
      .subscribe({
        next: (data) => {
          data.byLocation = data.byLocation ?? [];
          data.byName = data.byName ?? [];
          this.results = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Failed to load hotel search results';
          this.loading = false;
        }
      });
  }

  openHotelDetails(hotelId: number) {
    const url = `/hotel-details/${hotelId}`;
    window.open(url, '_blank');
  }
}
