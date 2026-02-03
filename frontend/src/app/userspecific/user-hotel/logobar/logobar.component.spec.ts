import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogobarComponent } from './logobar.component';

describe('LogobarComponent', () => {
  let component: LogobarComponent;
  let fixture: ComponentFixture<LogobarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogobarComponent]
    });
    fixture = TestBed.createComponent(LogobarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
