import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentRowActionsComponent } from './document-row-actions.component';

describe('DocumentRowActionsComponent', () => {
  let component: DocumentRowActionsComponent;
  let fixture: ComponentFixture<DocumentRowActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentRowActionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DocumentRowActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
