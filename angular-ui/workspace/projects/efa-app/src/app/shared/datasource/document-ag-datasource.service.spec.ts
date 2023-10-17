import { TestBed } from '@angular/core/testing';

import { DocumentAgDatasourceService } from './document-ag-datasource.service';

describe('DocumentAgDatasourceService', () => {
  let service: DocumentAgDatasourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentAgDatasourceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
