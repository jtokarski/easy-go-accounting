import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceDocumentBrowseComponent } from './source-document-browse.component';

describe('SourceDocumentBrowseComponent', () => {
  let component: SourceDocumentBrowseComponent;
  let fixture: ComponentFixture<SourceDocumentBrowseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourceDocumentBrowseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourceDocumentBrowseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
