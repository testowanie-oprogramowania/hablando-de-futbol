import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatePickerFieldComponent } from './date-picker-field.component';

describe('DatePickerFieldComponent', () => {
  let component: DatePickerFieldComponent;
  let fixture: ComponentFixture<DatePickerFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DatePickerFieldComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DatePickerFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
