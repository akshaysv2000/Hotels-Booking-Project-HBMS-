import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotellocationComponent } from './hotellocation.component';

describe('HotellocationComponent', () => {
  let component: HotellocationComponent;
  let fixture: ComponentFixture<HotellocationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotellocationComponent]
    });
    fixture = TestBed.createComponent(HotellocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
