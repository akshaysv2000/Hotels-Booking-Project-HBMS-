import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelbookingsComponent } from './hotelbookings.component';

describe('HotelbookingsComponent', () => {
  let component: HotelbookingsComponent;
  let fixture: ComponentFixture<HotelbookingsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelbookingsComponent]
    });
    fixture = TestBed.createComponent(HotelbookingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
