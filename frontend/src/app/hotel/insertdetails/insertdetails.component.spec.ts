import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertdetailsComponent } from './insertdetails.component';

describe('InsertdetailsComponent', () => {
  let component: InsertdetailsComponent;
  let fixture: ComponentFixture<InsertdetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InsertdetailsComponent]
    });
    fixture = TestBed.createComponent(InsertdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
