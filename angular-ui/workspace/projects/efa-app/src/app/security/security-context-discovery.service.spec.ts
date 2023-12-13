import { TestBed } from '@angular/core/testing';

import { SecurityContextDiscoveryService } from './security-context-discovery.service';

describe('SecurityContextDiscoveryService', () => {
  let service: SecurityContextDiscoveryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecurityContextDiscoveryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
