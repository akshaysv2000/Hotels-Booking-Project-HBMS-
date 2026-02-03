import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.css']
})
export class AdminLayoutComponent {
          
            constructor(private router: Router) {}
          
            logout() {
              // Clear all role tokens, or just admin token
              localStorage.removeItem('adminToken');
              // Optional: clear other role tokens if needed
              localStorage.removeItem('userToken');
              localStorage.removeItem('hotelToken');
          
              this.router.navigate(['/']);
            }
}
