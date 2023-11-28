import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateArticleFormComponent } from './create-article-form.component';

describe('CreateArticleFormComponent', () => {
  let component: CreateArticleFormComponent;
  let fixture: ComponentFixture<CreateArticleFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateArticleFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreateArticleFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
