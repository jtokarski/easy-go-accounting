import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceDocumentHomeComponent } from './source-document-home.component';

describe('SourceDocumentHomeComponent', () => {
  let component: SourceDocumentHomeComponent;
  let fixture: ComponentFixture<SourceDocumentHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourceDocumentHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourceDocumentHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
