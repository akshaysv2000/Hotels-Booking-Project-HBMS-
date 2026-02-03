import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HoteldashboardComponent } from './hoteldashboard.component';

describe('HoteldashboardComponent', () => {
  let component: HoteldashboardComponent;
  let fixture: ComponentFixture<HoteldashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HoteldashboardComponent]
    });
    fixture = TestBed.createComponent(HoteldashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
