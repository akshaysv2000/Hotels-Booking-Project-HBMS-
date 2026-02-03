import { Component, OnInit } from '@angular/core';
import { HotelDashboardService } from './hotel-dashboard.service';

@Component({
  selector: 'app-hoteldash',
  templateUrl: './hoteldash.component.html',
  styleUrls: ['./hoteldash.component.css']
})
export class HoteldashComponent implements OnInit {

  bookingsPerRoomTypeData: { name: string, value: number }[] = [];
  bookingsPerDayData: { name: string, value: number }[] = [];
  amountPerDayData: { name: string, value: number }[] = [];

  totalBookings: number = 0;
  totalAmount: number = 0;

  // ngx-charts options (customize as needed)
  view: [number, number] = [700, 400];
  showLegend = true;
  showLabels = true;
  animations = true;
  xAxis = true;
  yAxis = true;
  showYAxisLabel = true;
  showXAxisLabel = true;
  xAxisLabel = '';
  yAxisLabel = '';
  timeline = true;

  constructor(private dashService: HotelDashboardService) {}

  ngOnInit() {
    this.dashService.getStats().subscribe(stats => {
      this.totalBookings = stats.totalBookings;
      this.totalAmount = stats.totalAmountReceived;
    });

    this.dashService.getBookingsPerRoomType().subscribe(data => {
      this.bookingsPerRoomTypeData = data.map(d => ({ name: d.roomType, value: d.count }));
    });

    this.dashService.getBookingsPerDay().subscribe(data => {
      this.bookingsPerDayData = data.map(d => ({ name: d.date, value: d.bookingsCount }));
      this.xAxisLabel = 'Date';
      this.yAxisLabel = 'Bookings';
    });

    this.dashService.getAmountPerDay().subscribe(data => {
      this.amountPerDayData = data.map(d => ({ name: d.date, value: d.totalAmount }));
      // optionally use different labels for amount chart
    });
  }
}
