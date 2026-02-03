import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ResponseStructure } from '../models/response-structure.model';
import { User } from '../models/user.model';
import { HotelServiceService } from 'src/app/hotel/hotel-service.service';

@Component({
  selector: 'app-auth-overlay',
  templateUrl: './auth-overlay.component.html',
  styleUrls: ['./auth-overlay.component.css']
})
export class AuthOverlayComponent {
  @Output() closed = new EventEmitter<void>();

  isVisible = false;
  selectedRole: 'user' | 'hotel' | 'admin' = 'user';
  isRegistering = false;

  // Forms model data
  userLogin = { username: '', password: '' };
  userRegister = { name: '', email: '', username: '', password: '', phone: '' };
  hotelLogin = { username: '', password: '' };
  hotelRegister = {
  username: '',
  password: '',
  name: '',
  address: '',
  ownerName: '',
  contactNumber: '',
  location: ''
};


  constructor(
    private http: HttpClient,
    private router: Router,
    private hotelService: HotelServiceService 
  ) {}

  open() {
    this.isVisible = true;
  }

  closeOverlay() {
    this.isVisible = false;
    this.closed.emit();
  }

  selectRole(role: 'user' | 'hotel' | 'admin') {
    this.selectedRole = role;
    this.isRegistering = false;
    if (role === 'admin') {
      this.closeOverlay();
      this.router.navigate(['/adminlogin']);
    }
  }

  toggleRegister() {
    this.isRegistering = !this.isRegistering;
  }

  errorMessage: string = '';


  onUserLogin() {
    this.http.post<{ token: string }>('http://localhost:8080/user/login', this.userLogin).subscribe({
      next: res => {
        localStorage.setItem('userToken', res.token);
        this.closeOverlay();
        this.router.navigate(['/dashboard-shell']);
      },
      error: () => {
        this.errorMessage = 'Invalid username or password.';
      }
    });
  }

  onUserRegister() {
    this.http.post<ResponseStructure<User>>('http://localhost:8080/userRegistration', this.userRegister)
  .subscribe({
    next: (response) => {
      alert('Registration successful');
      this.isRegistering = false;
    },
    error: (err) => {
      alert(err.error?.message || 'Registration failed');
    }
  });
  }

onHotelLogin() {
  this.http.post<any>('http://localhost:8080/hotel/login', this.hotelLogin).subscribe({
    next: res => {
      if (res.token) {
        // Success: store token and navigate
        localStorage.setItem('hotelToken', res.token);
        this.closeOverlay();
        this.hotelService.checkHotelDetailsCompletion().subscribe(isComplete => {
          if (isComplete) {
            this.router.navigate(['/hotel-dashboard']);
          } else {
            this.router.navigate(['/hoteldetails']);
          }
        });
      } else if (res.message) {
        // Handle approval pending or rejected messages
        alert(res.message);
      } else {
        alert('Unknown response from server');
      }
    },
    error: err => {
      // Handle network/server errors or invalid credentials
      if (err.status === 403 && err.error && err.error.message) {
        alert(err.error.message); // Show message from backend if any
      } else {
        alert('Invalid credentials or server error');
      }
    }
  });
}


  onHotelRegister() {
    this.http.post('http://localhost:8080/hotelRegistration', this.hotelRegister).subscribe({
      next: () => {
        alert('Hotel registration successful');
        this.isRegistering = false;
         this.router.navigate(['/']);
      },
      error: () => alert('Registration failed')
    });
  }



  // Password show/hide state for user form
showUserPassword = false;

toggleUserPassword() {
  this.showUserPassword = !this.showUserPassword;
}

// Password show/hide state for user register form
showRegPassword = false;

toggleRegPasswordVisibility() {
  this.showRegPassword = !this.showRegPassword;
}


// Inside class AuthOverlayComponent
showHotelRegPassword = false;

toggleHotelRegPassword() {
  this.showHotelRegPassword = !this.showHotelRegPassword;
}

showHotelPassword = false;

toggleHotelPassword() {
  this.showHotelPassword = !this.showHotelPassword;
}

}
