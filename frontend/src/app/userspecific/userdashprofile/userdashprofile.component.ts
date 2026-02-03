import { Component } from '@angular/core';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-userdashprofile',
  templateUrl: './userdashprofile.component.html',
  styleUrls: ['./userdashprofile.component.css']
})
export class UserdashprofileComponent {
   name: string = '';
  activeTab: string = 'profile';

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.getUserProfile().subscribe(
      data => this.name = data.name
    );
  }

  setTab(tab: string) {
    this.activeTab = tab;
  }

}
