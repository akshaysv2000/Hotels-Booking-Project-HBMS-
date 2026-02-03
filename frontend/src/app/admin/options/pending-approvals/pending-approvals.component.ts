import { Component } from '@angular/core';
import { AdminPendinghotelService, HotelDTO } from './admin-pendinghotel.service';

@Component({
  selector: 'app-pending-approvals',
  templateUrl: './pending-approvals.component.html',
  styleUrls: ['./pending-approvals.component.css']
})
export class PendingApprovalsComponent {

  pendingHotels: HotelDTO[] = [];
  loading = false;

  constructor(private hotelService: AdminPendinghotelService) {}

  ngOnInit() {
    this.loadPendingHotels();
  }

  loadPendingHotels() {
    this.loading = true;
    this.hotelService.getPendingHotels().subscribe({
      next: hotels => {
        this.pendingHotels = hotels;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  approve(hotelId: number) {
    this.hotelService.approveHotel(hotelId).subscribe(() => this.loadPendingHotels());
  }

  reject(hotelId: number) {
    this.hotelService.rejectHotel(hotelId).subscribe(() => this.loadPendingHotels());
  }

}
