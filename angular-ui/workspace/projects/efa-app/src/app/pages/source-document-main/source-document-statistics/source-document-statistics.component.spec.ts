import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceDocumentStatisticsComponent } from './source-document-statistics.component';

describe('SourceDocumentStatisticsComponent', () => {
  let component: SourceDocumentStatisticsComponent;
  let fixture: ComponentFixture<SourceDocumentStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SourceDocumentStatisticsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SourceDocumentStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
