import { Component, OnInit } from '@angular/core';
import { AdminHotelsService, HotelDTO } from '../../services/admin-hotels.service';

@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {
  hotels: HotelDTO[] = [];
  loading = true;
  error = '';

  constructor(private hotelService: AdminHotelsService) {}

  ngOnInit(): void {
    this.hotelService.getApprovedHotels().subscribe({
      next: (data) => {
        this.hotels = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load hotels.';
        this.loading = false;
      }
    });
  }
}
