import { Component } from '@angular/core';
import { UserService } from '../../service/user.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css']
})
export class ProfileDetailsComponent {
  profileForm: FormGroup;
  editMode = false;

  constructor(private userService: UserService, private fb: FormBuilder) {
    this.profileForm = this.fb.group({
      username: [''],
      name: [''],
      email: [''],
      phone: [''],
      password: ['']
    });
  }

  ngOnInit() {
    this.userService.getUserProfile().subscribe(
      data => this.profileForm.patchValue(data)
    );
  }

  enableEdit() { this.editMode = true; }
  cancelEdit() { this.editMode = false; }

  saveProfile() {
    if (this.profileForm.valid) {
      this.userService.updateUserProfile(this.profileForm.value).subscribe(
        res => {
          alert("Profile updated!");
          this.editMode = false;
        }
      );
    }
  }

}
