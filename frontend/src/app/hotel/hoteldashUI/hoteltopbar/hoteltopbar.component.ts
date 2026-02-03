import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-hoteltopbar',
  templateUrl: './hoteltopbar.component.html',
  styleUrls: ['./hoteltopbar.component.css']
})
export class HoteltopbarComponent {

   @Input() isDarkMode!: boolean;
  @Output() toggleDark = new EventEmitter();

}
