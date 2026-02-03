import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface Room {
  roomType: string;
  pricePerNight: number;
  totalRooms: number;
  status?: 'pending' | 'success' | 'error';
}

interface Facility {
  name: string;
  status?: 'pending' | 'success' | 'error';
}

interface HotelImage {
  file: File;
  preview: string;   
  status?: 'pending' | 'success' | 'error';
}


@Component({
  selector: 'app-insertdetails',
  templateUrl: './insertdetails.component.html',
  styleUrls: ['./insertdetails.component.css']
})
export class InsertdetailsComponent {

  
  description = '';

 
  facilityName = '';
  facilities: Facility[] = [];

 
 roomType = '';
  pricePerNight: number | null = null;
  totalRooms: number | null = null;
  rooms: Room[] = [];

  
  images: HotelImage[] = [];

  
  submitting = false;
  submitError = '';

  constructor(private http: HttpClient, private router: Router) {}

  
  addFacility() {
    if (this.facilityName.trim()) {
      this.facilities.push({ name: this.facilityName.trim(), status: 'pending' });
      this.facilityName = '';
    }
  }
  removeFacility(idx: number) {
    this.facilities.splice(idx, 1);
  }

  // --- Rooms ---
 addRoom() {
  if (
    this.roomType.trim() &&
    this.pricePerNight !== null && !isNaN(this.pricePerNight) && this.pricePerNight > 0 &&
    this.totalRooms !== null && !isNaN(this.totalRooms) && this.totalRooms > 0
  ) {
    this.rooms.push({
      roomType: this.roomType.trim(),
      pricePerNight: this.pricePerNight,
      totalRooms: this.totalRooms,
      status: 'pending'
    });
    this.roomType = '';
    this.pricePerNight = null;
    this.totalRooms = null;
  }
}
  removeRoom(idx: number) {
    this.rooms.splice(idx, 1);
  }

  // --- Images ---
  onImageSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.images.push({
          file,
          preview: e.target.result,
          status: 'pending'
        });
      };
      reader.readAsDataURL(file);
    }
    // Reset input so same image can be re-added if needed
    event.target.value = '';
  }
  removeImage(idx: number) {
    this.images.splice(idx, 1);
  }

  // --- Submitting All ---
  async submitAll() {
  this.submitting = true;
  this.submitError = '';
  
  const descriptionDone = !this.description
    ? true
    : await this.submitDescription();

  const facilitiesPromises = this.facilities.map(facility =>
    this.http.post('http://localhost:8080/hotel/facilities', { facility: facility.name }).toPromise()
      .then(() => facility.status = 'success')
      .catch(() => facility.status = 'error')
  );
  await Promise.all(facilitiesPromises);

  const roomsPromises = this.rooms.map(room =>
    this.http.post('http://localhost:8080/hotel/rooms', {
      roomType: room.roomType,
      pricePerNight: room.pricePerNight,
      totalRooms: room.totalRooms
    }).toPromise()
      .then(() => room.status = 'success')
      .catch(() => room.status = 'error')
  );
  await Promise.all(roomsPromises);

  const imagesPromises = this.images.map(image => {
    const formData = new FormData();
    formData.append('file', image.file);
    return this.http.post('http://localhost:8080/hotel/images', formData).toPromise()
      .then(() => image.status = 'success')
      .catch(() => image.status = 'error');
  });
  await Promise.all(imagesPromises);

  this.submitting = false;

  if (
    this.facilities.some(f => f.status === 'error') ||
    this.rooms.some(r => r.status === 'error') ||
    this.images.some(i => i.status === 'error') ||
    !descriptionDone
  ) {
    this.submitError = 'Some items failed to submit. Please retry failed items.';
  } else {
    alert('All details submitted!');
    // Redirect to hotel location page
    this.router.navigate(['/hotel-location']);  // Adjust route path if necessary
  }
}


  async submitDescription(): Promise<boolean> {
    try {
      await this.http.post('http://localhost:8080/hotel/description', { description: this.description }).toPromise();
      return true;
    } catch {
      this.submitError = 'Failed to submit hotel description.';
      return false;
    }
  }

  // --- Retry Individual Failures ---
  retryFacility(idx: number) {
    const facility = this.facilities[idx];
    facility.status = 'pending';
    this.http.post('http://localhost:8080/hotel/facilities', { facility: facility.name }).subscribe({
      next: () => facility.status = 'success',
      error: () => facility.status = 'error'
    });
  }

  retryRoom(idx: number) {
    const room = this.rooms[idx];
    room.status = 'pending';
    this.http.post('http://localhost:8080/hotel/rooms', {
      roomType: room.roomType,
      pricePerNight: room.pricePerNight,
      totalRooms: room.totalRooms
    }).subscribe({
      next: () => room.status = 'success',
      error: () => room.status = 'error'
    });
  }

  retryImage(idx: number) {
    const image = this.images[idx];
    image.status = 'pending';
    const formData = new FormData();
    formData.append('file', image.file);
    this.http.post('http://localhost:8080/hotel/images', formData).subscribe({
      next: () => image.status = 'success',
      error: () => image.status = 'error'
    });
  }

}
