import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelprofileComponent } from './hotelprofile.component';

describe('HotelprofileComponent', () => {
  let component: HotelprofileComponent;
  let fixture: ComponentFixture<HotelprofileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelprofileComponent]
    });
    fixture = TestBed.createComponent(HotelprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
