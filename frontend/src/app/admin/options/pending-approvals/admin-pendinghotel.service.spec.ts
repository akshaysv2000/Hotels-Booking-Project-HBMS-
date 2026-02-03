import { TestBed } from '@angular/core/testing';

import { AdminPendinghotelService } from './admin-pendinghotel.service';

describe('AdminPendinghotelService', () => {
  let service: AdminPendinghotelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminPendinghotelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
