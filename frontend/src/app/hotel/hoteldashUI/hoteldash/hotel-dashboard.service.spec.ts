import { TestBed } from '@angular/core/testing';

import { HotelDashboardService } from './hotel-dashboard.service';

describe('HotelDashboardService', () => {
  let service: HotelDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
