import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adminlogin',
  templateUrl: './adminlogin.component.html',
  styleUrls: ['./adminlogin.component.css']
})
export class AdminloginComponent {
admin = { username: '', password: '' };

  errorMessage: string = '';
 constructor(private http: HttpClient, private router: Router) {}

 showPassword: boolean = false;

togglePassword() {
  this.showPassword = !this.showPassword;
}

onAdminLogin() {
  this.http.post<{ token: string }>('http://localhost:8080/admin/login', this.admin)
    .subscribe({
      next: (res) => {
        localStorage.setItem('adminToken', res.token);

         localStorage.removeItem('userToken');
          localStorage.removeItem('hotelToken');

        this.router.navigate(['/admin/dashboard']);
      },
      error: () => {
        this.errorMessage = 'Invalid credentials';
      }
    });
}

  
}
