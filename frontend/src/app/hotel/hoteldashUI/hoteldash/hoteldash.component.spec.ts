import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HoteldashComponent } from './hoteldash.component';

describe('HoteldashComponent', () => {
  let component: HoteldashComponent;
  let fixture: ComponentFixture<HoteldashComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HoteldashComponent]
    });
    fixture = TestBed.createComponent(HoteldashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
