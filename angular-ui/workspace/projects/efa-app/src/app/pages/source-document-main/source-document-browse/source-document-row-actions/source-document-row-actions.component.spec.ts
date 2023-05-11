import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceDocumentRowActionsComponent } from './source-document-row-actions.component';

describe('SourceDocumentRowActionsComponent', () => {
  let component: SourceDocumentRowActionsComponent;
  let fixture: ComponentFixture<SourceDocumentRowActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourceDocumentRowActionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourceDocumentRowActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
