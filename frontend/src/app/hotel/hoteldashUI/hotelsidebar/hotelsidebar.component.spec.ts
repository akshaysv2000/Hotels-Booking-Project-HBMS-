import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelsidebarComponent } from './hotelsidebar.component';

describe('HotelsidebarComponent', () => {
  let component: HotelsidebarComponent;
  let fixture: ComponentFixture<HotelsidebarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelsidebarComponent]
    });
    fixture = TestBed.createComponent(HotelsidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
