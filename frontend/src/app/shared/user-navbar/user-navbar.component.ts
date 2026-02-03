import { Component, NgZone, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';

declare const google: any;

@Component({
  selector: 'app-user-navbar',
  templateUrl: './user-navbar.component.html',
  styleUrls: ['./user-navbar.component.css']
})
export class UserNavbarComponent {

  searchTerm: string = '';
  latitude?: number;
  longitude?: number;
  autocomplete!: any;


   constructor(private router: Router, private ngZone: NgZone) {}

     ngAfterViewInit() {
    const input = document.getElementById('autocomplete-input') as HTMLInputElement;

    this.autocomplete = new google.maps.places.Autocomplete(input, {
      types: ['geocode', 'establishment']
    });

    this.autocomplete.addListener('place_changed', () => {
      this.ngZone.run(() => {
        const place = this.autocomplete.getPlace();
        if (place.geometry) {
          this.latitude = place.geometry.location.lat();
          this.longitude = place.geometry.location.lng();
          this.searchTerm = input.value;
        }
      });
    });
  }



  logout() {
    // clear token / user data
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  onSearch() {
  console.log("Search triggered with:", this.searchTerm);
  if (this.searchTerm && this.searchTerm.trim()) {
    const trimmedTerm = this.searchTerm.trim();                           
    this.router.navigate(['/dashboard-shell/search-results'], {
      queryParams: {
        name: trimmedTerm,
        location: trimmedTerm,
        latitude: this.latitude,
        longitude: this.longitude
      }
    });
  }
}


}
