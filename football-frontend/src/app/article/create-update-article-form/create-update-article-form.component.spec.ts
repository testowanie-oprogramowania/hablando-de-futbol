import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateArticleFormComponent } from './create-update-article-form.component';

describe('CreateArticleFormComponent', () => {
  let component: CreateUpdateArticleFormComponent;
  let fixture: ComponentFixture<CreateUpdateArticleFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateUpdateArticleFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateUpdateArticleFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
