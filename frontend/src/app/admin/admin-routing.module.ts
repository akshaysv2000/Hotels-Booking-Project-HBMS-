import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { DashComponent } from './options/dash/dash.component';
import { UsersComponent } from './options/users/users.component';
import { HotelComponent } from './options/hotel/hotel.component';
import { PendingApprovalsComponent } from './options/pending-approvals/pending-approvals.component';
import { ProfileComponent } from './options/profile/profile.component';
import { AdminLayoutComponent } from './admin-layout/admin-layout.component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminLayoutComponent, // wrapper with sidebar/navbar
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }, // default landing
      { path: 'dashboard', component: DashComponent },
      { path: 'users', component: UsersComponent },
      { path: 'hotels', component: HotelComponent },
      { path: 'pending-approvals', component: PendingApprovalsComponent },
      { path: 'profile', component: ProfileComponent },
    ]
  }
];


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
   exports: [RouterModule]
})
export class AdminRoutingModule { }
