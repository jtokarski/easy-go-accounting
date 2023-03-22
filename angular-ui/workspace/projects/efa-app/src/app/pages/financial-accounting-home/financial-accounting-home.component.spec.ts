import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinancialAccountingHomeComponent } from './financial-accounting-home.component';

describe('FinancialAccountingHomeComponent', () => {
  let component: FinancialAccountingHomeComponent;
  let fixture: ComponentFixture<FinancialAccountingHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FinancialAccountingHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinancialAccountingHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
