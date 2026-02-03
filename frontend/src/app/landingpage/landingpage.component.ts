import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthOverlayComponent } from '../landpageUI/auth-overlay/auth-overlay.component';

declare var bootstrap: any;

@Component({
  selector: 'app-landingpage',
  templateUrl: './landingpage.component.html',
  styleUrls: ['./landingpage.component.css']
})
export class LandingpageComponent {
  destinations: string[] = ['Goa', 'Delhi', 'Mumbai', 'Jaipur', 'Bangalore', 'Chennai'];

  constructor(private http: HttpClient, private router: Router) {}
  

   @ViewChild('authOverlay') authOverlay!: AuthOverlayComponent;

  openAuthOverlay() {
    this.authOverlay.open();
  }
   redirectLogin() {
    this.openAuthOverlay();
  }

admin = { username: '', password: '' };


}
