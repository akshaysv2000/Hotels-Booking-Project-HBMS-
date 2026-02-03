import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HoteltopbarComponent } from './hoteltopbar.component';

describe('HoteltopbarComponent', () => {
  let component: HoteltopbarComponent;
  let fixture: ComponentFixture<HoteltopbarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HoteltopbarComponent]
    });
    fixture = TestBed.createComponent(HoteltopbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
