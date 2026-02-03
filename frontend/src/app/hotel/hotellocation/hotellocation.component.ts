import { HttpClient } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { MapMarker } from '@angular/google-maps';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hotellocation',
  templateUrl: './hotellocation.component.html',
  styleUrls: ['./hotellocation.component.css']
})
export class HotellocationComponent {

   @ViewChild(MapMarker) marker!: MapMarker;

  constructor(private http: HttpClient, private router: Router) {}

  zoom = 12;
  center: google.maps.LatLngLiteral = { lat: 20.5937, lng: 78.9629 };
  markerPosition: google.maps.LatLngLiteral | null = null;

  setMarker(event: google.maps.MapMouseEvent) {
    if (event.latLng) {
      this.markerPosition = {
        lat: event.latLng.lat(),
        lng: event.latLng.lng(),
      };
    }
  }

  onMarkerDragEnd(event: DragEvent) {
    const position = this.marker.getPosition();
    if (position) {
      this.markerPosition = {
        lat: position.lat(),
        lng: position.lng()
      };
    }
  }

  submitLocation() {
  if (this.markerPosition) {
    this.http.post('http://localhost:8080/hotel/location', this.markerPosition)
      .subscribe({
        next: () => {
          alert('Location Saved!');
          this.router.navigate(['/hotel-dashboard']); // Change this to your dashboard route
        },
        error: () => alert('Could not save location')
      });
  } else {
    alert('Please select a location on the map.');
  }
}

}
