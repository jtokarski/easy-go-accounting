import { TestBed } from '@angular/core/testing';

import { SourceDocumentAgDatasourceService } from './source-document-ag-datasource.service';

describe('SourceDocumentAgDatasourceService', () => {
  let service: SourceDocumentAgDatasourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SourceDocumentAgDatasourceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
