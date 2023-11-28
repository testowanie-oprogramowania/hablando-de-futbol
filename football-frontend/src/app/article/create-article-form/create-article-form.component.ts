import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
    FormBuilder,
    FormControl,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { Editor } from '../../models/editor';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { Observable, of } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { Category } from '../../models/category';
import { MatCardModule } from '@angular/material/card';
import { TextInputFieldComponent } from '../../shared/text-input-field/text-input-field.component';
import { TextAreaFieldComponent } from '../../shared/text-area-field/text-area-field.component';
import { DatePickerFieldComponent } from '../../shared/date-picker-field/date-picker-field.component';
import { SelectFieldComponent } from '../../shared/select-field/select-field.component';
import { Article, ArticleRawFormValue } from '../../models/article';
import { ArticleService } from '../../services/article.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-create-article-form',
    standalone: true,
    imports: [
        CommonModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        MatDatepickerModule,
        MatSelectModule,
        MatButtonModule,
        FormsModule,
        MatNativeDateModule,
        MatFormFieldModule,
        MatCardModule,
        TextInputFieldComponent,
        TextAreaFieldComponent,
        DatePickerFieldComponent,
        SelectFieldComponent,
    ],
    templateUrl: './create-article-form.component.html',
    styleUrl: './create-article-form.component.scss',
})
export class CreateArticleFormComponent {
    articleForm = this.formBuilder.group({
        title: ['', Validators.required],
        editor: [undefined as Editor | undefined, Validators.required],
        publicationDate: [undefined as Date | undefined, Validators.required],
        content: ['', Validators.required],
        photoUrl: ['', Validators.required],
        category: [undefined as Category | undefined, Validators.required],
    });

    categories$: Observable<Category[]> = of();
    categoryFormToShow = (category: Category) => category.name;

    editors$: Observable<Editor[]> = of();
    editorFormToShow = (editor: Editor) => editor.name;

    articleContentRowsNumber = 15;

    constructor(
        private readonly articleService: ArticleService,
        private readonly formBuilder: FormBuilder,
        private readonly router: Router
    ) {}

    submitForm() {
        const article = Article.fromForm(
            this.articleForm.value as ArticleRawFormValue
        );
        this.articleService.createArticle(article).subscribe({
            next: () => {
                this.router.navigate(['/articles']).then(r => {});
            },
        });
    }

    goBack() {
        this.router.navigate(['/articles']).then(r => {});
    }
}
