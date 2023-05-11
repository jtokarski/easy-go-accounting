import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceDocumentDetailsComponent } from './source-document-details.component';

describe('SourceDocumentDetailsComponent', () => {
  let component: SourceDocumentDetailsComponent;
  let fixture: ComponentFixture<SourceDocumentDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourceDocumentDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourceDocumentDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
