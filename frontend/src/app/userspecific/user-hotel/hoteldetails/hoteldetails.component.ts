import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookingServiceService } from '../services/booking-service.service';

declare var Razorpay: any;


@Component({
  selector: 'app-hoteldetails',
  templateUrl: './hoteldetails.component.html',
  styleUrls: ['./hoteldetails.component.css']
})
export class HoteldetailsComponent implements OnInit {
hotelId!: number;
  hotelDetails: any;
  loading = true;
  error = '';
  
  newReview = {
    rating: 5,
    comment: ''
  };
  submittingReview = false;

  availability = {
  roomId: null,
  checkIn: '',
  checkOut: '',
  numberOfRooms: 1
  };

  bookingData: {
  hotelId: number;
  roomId: number | null;
  checkInDate: string | null;
  checkOutDate: string | null;
  numberOfRooms: number;
} = {
  hotelId: 0,
  roomId: null,
  checkInDate: null,
  checkOutDate: null,
  numberOfRooms: 1,
};


  imagesToShow: string[] = [];
  showAllImages = false;

  constructor(private route: ActivatedRoute, private http: HttpClient,private bookingService: BookingServiceService) {}

  ngOnInit(): void {
    this.hotelId = Number(this.route.snapshot.paramMap.get('hotelId'));
    this.bookingData.hotelId = this.hotelId;
    this.loadHotelDetails();
  }

  loadHotelDetails() {
    this.loading = true;
    this.http.get(`http://localhost:8080/user/hotels/${this.hotelId}/details`).subscribe({
      next: (data: any) => {
        this.hotelDetails = data;
        this.loading = false;
        this.prepareImagesForDisplay();
      },
      error: () => {
        this.error = 'Failed to load hotel details.';
        this.loading = false;
      }
    });
  }

  prepareImagesForDisplay() {
    if (!this.hotelDetails.imageUrls) return;
    if (this.hotelDetails.imageUrls.length <= 4 || this.showAllImages) {
      this.imagesToShow = this.hotelDetails.imageUrls;
    } else {
      // Show first 4 images by default
      this.imagesToShow = this.hotelDetails.imageUrls.slice(0, 4);
    }
  }

  showMoreImages() {
    this.showAllImages = true;
    this.prepareImagesForDisplay();
  }

  submitReview() {
    if (!this.newReview.comment || this.newReview.rating < 1 || this.newReview.rating > 5) return;
    this.submittingReview = true;
    this.http.post(`http://localhost:8080/user/hotels/user/${this.hotelId}/reviews`, this.newReview).subscribe({
      next: (res) => {
        alert('Review submitted successfully.');
        this.newReview = { rating: 5, comment: '' };
        this.loadHotelDetails(); // Reload to show new review
        this.submittingReview = false;
      },
      error: (err) => {
        alert('Failed to submit review. Please try again.');
        this.submittingReview = false;
      }
    });
  }


  isAvailable = false;
availabilityMessage = '';
showBookingForm = false;
bookingSubmitting = false;

checkAvailability() {
  this.availabilityMessage = '';
  this.isAvailable = false;

  // Validate inputs before sending
  if (!this.availability.roomId || !this.availability.checkIn || !this.availability.checkOut || this.availability.numberOfRooms < 1) {
    this.availabilityMessage = 'Please fill all fields correctly.';
    return;
  }

  // Call your backend API to check availability (use your service)
  this.bookingService.checkAvailability(
    this.availability.roomId,
    this.availability.checkIn,
    this.availability.checkOut,
    this.availability.numberOfRooms
  ).subscribe({
    next: (response) => {
      if (response.available) {
        this.isAvailable = true;
        this.availabilityMessage = `Rooms are available. Total price: ₹${response.totalPrice}`;
      } else {
        this.isAvailable = false;
        this.availabilityMessage = 'Requested rooms are not available for selected dates.';
      }
    },
    error: (err) => {
      this.isAvailable = false;
      this.availabilityMessage = 'Error checking availability: ' + err.message;
    }
  });
}

toggleBookingForm() {
  // Auto fill bookingData with availability data for convenience
  if (this.isAvailable) {
    this.bookingData.roomId = this.availability.roomId;
    this.bookingData.checkInDate = this.availability.checkIn;
    this.bookingData.checkOutDate = this.availability.checkOut;
    this.bookingData.numberOfRooms = this.availability.numberOfRooms;
  }
  this.showBookingForm = !this.showBookingForm;
}





initiateBooking() {
  this.bookingSubmitting = true;
  this.bookingService.createBookingWithPayment(this.bookingData).subscribe({
    next: (response: any) => {
      this.openRazorpayPayment(response.razorpayOrderId, response.amount, response.booking.bookingId);
    },
    error: (err) => {
      alert("Booking failed: " + (err.error || err.message));
      this.bookingSubmitting = false;
    }
  });
}

openRazorpayPayment(orderId: string, amount: number, bookingId: number) {
  const options = {
    key: "rzp_test_R85gcttS8hfK9M",
    amount: amount, // amount in paise
    currency: "INR",
    name: "Hotel Booking",
    description: "Room Booking Payment",
    order_id: orderId,
    handler: (response: { razorpay_order_id: string; razorpay_payment_id: string; razorpay_signature: string }) => {
      this.bookingService.verifyPayment({
       razorpayOrderId: response.razorpay_order_id,
       razorpayPaymentId: response.razorpay_payment_id,
       razorpaySignature: response.razorpay_signature,
       bookingId: bookingId
      }).subscribe({
                next: (res: any) => {
         
  if (res && res.text && res.text.includes('Payment verified successfully')) {
    alert("Payment Successful and Booking Confirmed");
    this.toggleBookingForm();
  } else if (res && res.error) {
    alert("Payment verification error: " + JSON.stringify(res.error));
  } else {
    alert("Payment verification response: " + JSON.stringify(res));
  }
  this.bookingSubmitting = false;
        },
        error: (err) => {
          if (err.error && err.error.error) {
    alert("Payment verification failed: " + JSON.stringify(err.error.error));
  } else if (err.error && err.error.text) {
    alert("Payment verification: " + err.error.text);
  } else {
    alert("Payment verification failed: " + (err.message || 'Unknown error'));
  }
  this.bookingSubmitting = false;
        }
      });
    },
    prefill: {
      // optionally add user details here
    },
    theme: {
      color: "#3399cc"
    }
  };

  const rzp = new Razorpay(options);
  rzp.open();
  rzp.on('payment.failed', (response: { error: { description: string } }) => {
    alert("Payment Failed: " + response.error.description);
    this.bookingSubmitting = false;
  });
}
}
