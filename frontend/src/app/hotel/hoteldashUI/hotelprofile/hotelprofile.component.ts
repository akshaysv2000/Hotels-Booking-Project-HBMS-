import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

interface HotelDTO {
  hotelId: number;
  name: string;
  address: string;
  ownerName: string;
  contactNumber: string;
  status: string;
  createdAt: string;
  location: string;
}

interface HotelUpdateDTO {
  username: string;
  password: string;
  contactNumber: string;
}
@Component({
  selector: 'app-hotelprofile',
  templateUrl: './hotelprofile.component.html',
  styleUrls: ['./hotelprofile.component.css']
})
export class HotelprofileComponent implements OnInit {
  hotel: HotelDTO | null = null;
  profileForm: FormGroup;
  editMode = false;
  updateMsg = '';

  constructor(private http: HttpClient, private fb: FormBuilder) {
    this.profileForm = this.fb.group({
      username: [''],
      password: [''],
      contactNumber: [''],
    });
  }

  ngOnInit() {
    this.getHotelProfile();
  }

  getHotelProfile() {
    this.http.get<HotelDTO>('http://localhost:8080/hotel/profile').subscribe((data) => {
      this.hotel = data;
      
      this.profileForm.patchValue({
        contactNumber: data.contactNumber,
      });
    });
  }

  enableEdit() {
    this.editMode = true;
    this.profileForm.reset({
      contactNumber: this.hotel?.contactNumber || '',
    });
  }

  updateProfile() {
    const updateDto: HotelUpdateDTO = {
      username: this.profileForm.value.username,
      password: this.profileForm.value.password,
      contactNumber: this.profileForm.value.contactNumber,
    };

    this.http.put<HotelDTO>('http://localhost:8080/hotel/updateprofile', updateDto).subscribe(
      (data) => {
        this.hotel = data;
        this.updateMsg = 'Profile updated successfully!';
        this.editMode = false;
      },
      (error) => {
        this.updateMsg = 'Error updating profile.';
      }
    );
  }
}
