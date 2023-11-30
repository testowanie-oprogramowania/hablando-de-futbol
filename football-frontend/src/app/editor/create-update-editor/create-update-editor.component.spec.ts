import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateEditorComponent } from './create-update-editor.component';

describe('CreateUpdateEditorComponent', () => {
  let component: CreateUpdateEditorComponent;
  let fixture: ComponentFixture<CreateUpdateEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateUpdateEditorComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateUpdateEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
