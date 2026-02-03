import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthOverlayComponent } from './auth-overlay.component';

describe('AuthOverlayComponent', () => {
  let component: AuthOverlayComponent;
  let fixture: ComponentFixture<AuthOverlayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuthOverlayComponent]
    });
    fixture = TestBed.createComponent(AuthOverlayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
