import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  admin: any = null;
  editMode = false;
  editForm: FormGroup;
  errorMessage = '';

  constructor(private http: HttpClient, private fb: FormBuilder) {
    // Set up the form for username and password with validation
    this.editForm = this.fb.group({
      newUsername: ['', Validators.required],
      newPassword: ['']
    });
  }

  ngOnInit() {
    this.loadProfile();
  }

  // Fetch the current admin profile from the backend
  loadProfile() {
    this.http.get('http://localhost:8080/admin/profile').subscribe({
      next: (data) => {
        this.admin = data;
        this.errorMessage = '';
      },
      error: () => {
        this.errorMessage = 'Failed to load admin profile.';
      }
    });
  }

  // Enable the edit mode and pre-fill the form with current username
  enableEdit() {
    this.editMode = true;
    this.editForm.patchValue({
      newUsername: this.admin.username,
      newPassword: ''
    });
  }

  cancelEdit() {
    this.editMode = false;
    this.errorMessage = '';
  }

  // Send PATCH request to update username/password
  saveChanges() {
    if (this.editForm.invalid) return;

    this.http.patch('http://localhost:8080/admin/profileEdit', this.editForm.value).subscribe({
      next: (updatedAdmin) => {
        this.admin = updatedAdmin;
        this.editMode = false;
        this.errorMessage = '';
      },
      error: (error: HttpErrorResponse) => {
        // Show backend error message or default one
        this.errorMessage = error.error?.message || 'Failed to update profile.';
      }
    });
  }
}