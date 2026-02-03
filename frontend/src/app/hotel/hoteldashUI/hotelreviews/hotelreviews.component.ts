import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';


interface ReviewDTO {
  reviewDate: string;
  comment: string;
  rating: number;
}

@Component({
  selector: 'app-hotelreviews',
  templateUrl: './hotelreviews.component.html',
  styleUrls: ['./hotelreviews.component.css']
})
export class HotelreviewsComponent implements OnInit{
  reviews: ReviewDTO[] = [];
  loading = true;
  error: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<ReviewDTO[]>('http://localhost:8080/hotel/reviews').subscribe({
      next: data => { this.reviews = data; this.loading = false; },
      error: () => { this.error = 'Could not load reviews.'; this.loading = false; }
    });
  }

}
