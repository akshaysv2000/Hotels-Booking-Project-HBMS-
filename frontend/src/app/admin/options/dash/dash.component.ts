import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

interface DashboardStats {
  totalUsers: number;
  totalApprovedHotels: number;
  totalPaidAmount: number;
}

@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.css']
})
export class DashComponent implements OnInit {
  stats?: DashboardStats;
  loading = true;
  error = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<DashboardStats>('http://localhost:8080/admin/dashboard/stats')
      .subscribe({
        next: data => {
          this.stats = data;
          this.loading = false;
        },
        error: err => {
          this.error = 'Failed to fetch dashboard stats';
          this.loading = false;
        }
      });
  }
}
