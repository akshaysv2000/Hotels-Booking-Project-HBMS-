import { Component,OnInit } from '@angular/core';
import { UserDTO,AdminUserService } from '../../services/admin-user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit{
  users: UserDTO[] = [];
  loading = true;
  error = '';

  constructor(private adminUserService: AdminUserService) { }

  ngOnInit(): void {
    this.adminUserService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load users.';
        this.loading = false;
      }
    });
  }

}
