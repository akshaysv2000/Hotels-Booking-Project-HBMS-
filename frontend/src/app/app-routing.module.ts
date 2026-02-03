import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingpageComponent } from './landingpage/landingpage.component';
import { DashboardShellComponent } from './dashboard/dashboard-shell/dashboard-shell.component';
import { PopularDestComponent } from './shared/popular-dest/popular-dest.component';
import { SearchresultComponent } from './userspecific/searchresult/searchresult.component';
import { AdminloginComponent } from './admin/adminlogin/adminlogin.component';
import { DashComponent } from './admin/options/dash/dash.component';
import { UsersComponent } from './admin/options/users/users.component';
import { HotelComponent } from './admin/options/hotel/hotel.component';
import { PendingApprovalsComponent } from './admin/options/pending-approvals/pending-approvals.component';
import { ProfileComponent } from './admin/options/profile/profile.component';
import { AdminLayoutComponent } from './admin/admin-layout/admin-layout.component';
import { HoteldashboardComponent } from './hotel/hoteldashboard/hoteldashboard.component';
import { InsertdetailsComponent } from './hotel/insertdetails/insertdetails.component';
import { HotellocationComponent } from './hotel/hotellocation/hotellocation.component';
import { HoteldashComponent } from './hotel/hoteldashUI/hoteldash/hoteldash.component';
import { HotelprofileComponent } from './hotel/hoteldashUI/hotelprofile/hotelprofile.component';
import { HotelbookingsComponent } from './hotel/hoteldashUI/hotelbookings/hotelbookings.component';
import { HotelreviewsComponent } from './hotel/hoteldashUI/hotelreviews/hotelreviews.component';
import { HoteldetailsComponent } from './userspecific/user-hotel/hoteldetails/hoteldetails.component';
import { UserdashprofileComponent } from './userspecific/userdashprofile/userdashprofile.component';


const routes: Routes = [
  {path:'',component:LandingpageComponent},
  {
    path: 'dashboard-shell',
    component: DashboardShellComponent,
    children: [
      { path: '', component: PopularDestComponent },  // default content
      { path: 'search-results', component: SearchresultComponent }
    ]
  },
  { path: 'adminlogin', component: AdminloginComponent },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashComponent },
      { path: 'users', component: UsersComponent },
      { path: 'hotels', component: HotelComponent },
      { path: 'pending-approvals', component: PendingApprovalsComponent },
      { path: 'profile', component: ProfileComponent },
      
    ]
  },
 { path:'hoteldetails',component: InsertdetailsComponent},
 {path:'hotel-location',component:HotellocationComponent},
 {path:'hotel-dashboard',component: HoteldashboardComponent,
  children: [
      { path: '', component: HoteldashComponent },          
      { path: 'profile', component: HotelprofileComponent },
      { path: 'bookings', component: HotelbookingsComponent },
      { path: 'reviews', component: HotelreviewsComponent },
    ]
 },
 {path:"hotel-details/:hotelId",component:HoteldetailsComponent},
 {path:"userdash-profile",component:UserdashprofileComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
