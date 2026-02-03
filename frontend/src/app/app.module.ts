import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LandingpageComponent } from './landingpage/landingpage.component';


import { DashboardShellComponent } from './dashboard/dashboard-shell/dashboard-shell.component';
import { UserNavbarComponent } from './shared/user-navbar/user-navbar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { PopularDestComponent } from './shared/popular-dest/popular-dest.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';

import { SearchresultComponent } from './userspecific/searchresult/searchresult.component';
import { AdminloginComponent } from './admin/adminlogin/adminlogin.component';
import { AuthInterceptorService } from './authint/auth-interceptor.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { DashComponent } from './admin/options/dash/dash.component';
import { UsersComponent } from './admin/options/users/users.component';
import { HotelComponent } from './admin/options/hotel/hotel.component';
import { PendingApprovalsComponent } from './admin/options/pending-approvals/pending-approvals.component';
import { ProfileComponent } from './admin/options/profile/profile.component';
import { AdminRoutingModule } from './admin/admin-routing.module';
import { AdminLayoutComponent } from './admin/admin-layout/admin-layout.component';
import { AuthOverlayComponent } from './landpageUI/auth-overlay/auth-overlay.component';
import { HoteldashboardComponent } from './hotel/hoteldashboard/hoteldashboard.component';
import { InsertdetailsComponent } from './hotel/insertdetails/insertdetails.component';
import { HotellocationComponent } from './hotel/hotellocation/hotellocation.component';
import { GoogleMapsModule } from '@angular/google-maps';
import { HoteldashComponent } from './hotel/hoteldashUI/hoteldash/hoteldash.component';
import { HotelprofileComponent } from './hotel/hoteldashUI/hotelprofile/hotelprofile.component';
import { HotelbookingsComponent } from './hotel/hoteldashUI/hotelbookings/hotelbookings.component';
import { HotelreviewsComponent } from './hotel/hoteldashUI/hotelreviews/hotelreviews.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HotelsidebarComponent } from './hotel/hoteldashUI/hotelsidebar/hotelsidebar.component';
import { HoteltopbarComponent } from './hotel/hoteldashUI/hoteltopbar/hoteltopbar.component';
import { ThemeToggleComponent } from './hotel/hoteldashUI/theme-toggle/theme-toggle.component';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HoteldetailsComponent } from './userspecific/user-hotel/hoteldetails/hoteldetails.component';
import { LogobarComponent } from './userspecific/user-hotel/logobar/logobar.component';
import { UserdashprofileComponent } from './userspecific/userdashprofile/userdashprofile.component';
import { ProfileDetailsComponent } from './userspecific/userdashprofile/profile-details/profile-details.component';
import { BookingListComponent } from './userspecific/userdashprofile/booking-list/booking-list.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';


@NgModule({
  declarations: [
    AppComponent,
    LandingpageComponent,
    
    DashboardShellComponent,
    UserNavbarComponent,
    FooterComponent,
    PopularDestComponent,
    
    SearchresultComponent,
    AdminloginComponent,
    DashComponent,
    UsersComponent,
    HotelComponent,
    PendingApprovalsComponent,
    ProfileComponent,
    AdminLayoutComponent,
    AuthOverlayComponent,
    HoteldashboardComponent,
    InsertdetailsComponent,
    HotellocationComponent,
    HoteldashComponent,
    HotelprofileComponent,
    HotelbookingsComponent,
    HotelreviewsComponent,
    HotelsidebarComponent,
    HoteltopbarComponent,
    ThemeToggleComponent,
    HoteldetailsComponent,
    LogobarComponent,
    UserdashprofileComponent,
    ProfileDetailsComponent,
    BookingListComponent,
    
    
    
    
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
     FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AdminRoutingModule,
    GoogleMapsModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatToolbarModule,
    NgxChartsModule
    
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
