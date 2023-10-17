import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentBrowseComponent } from './document-browse.component';

describe('DocumentBrowseComponent', () => {
  let component: DocumentBrowseComponent;
  let fixture: ComponentFixture<DocumentBrowseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentBrowseComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DocumentBrowseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
