import { Component } from '@angular/core';

@Component({
  selector: 'app-hoteldashboard',
  templateUrl: './hoteldashboard.component.html',
  styleUrls: ['./hoteldashboard.component.css']
})
export class HoteldashboardComponent {
   isDarkMode = false;

  toggleDarkMode() {
    this.isDarkMode = !this.isDarkMode;
  }

}
