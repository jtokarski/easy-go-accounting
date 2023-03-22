import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceDocumentMainComponent } from './source-document-main.component';

describe('SourceDocumentMainComponent', () => {
  let component: SourceDocumentMainComponent;
  let fixture: ComponentFixture<SourceDocumentMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourceDocumentMainComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourceDocumentMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
