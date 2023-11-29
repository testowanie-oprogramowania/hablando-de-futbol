import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
    FormBuilder,
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
import { ArticleService } from '../../services/article.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CategoryService} from "../../services/category.service";
import {EditorService} from "../../services/editor.service";
import {ArticleRequest, ArticleRequestRawFormValue} from "../../models/article-request";

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



    categories$: Observable<Category[]>;
    categoryFormToShow = (category: Category) => category.name;

    editors$: Observable<Editor[]> = of();
    editorFormToShow = (editor: Editor) => editor.name + ' ' + editor.surname;

    articleContentRowsNumber = 15;

    constructor(
        private readonly articleService: ArticleService,
        private readonly categoryService: CategoryService,
        private readonly editorService: EditorService,
        private readonly activatedRoute: ActivatedRoute,
        private readonly formBuilder: FormBuilder,
        private readonly router: Router
    ) {
        this.categories$ = categoryService.getAllCategories();
        this.editors$ = editorService.getEditors({ page: 0, size: 100 });

        const a = this.activatedRoute.snapshot.paramMap.get('id');
        console.log(a);
    }

    submitForm() {
        console.log('article form');
        console.log(this.articleForm.value);
        console.log('article form value');
        console.log(this.articleForm.value as ArticleRequestRawFormValue);
        const articleRequest = ArticleRequest.fromForm(
            this.articleForm.value as ArticleRequestRawFormValue
        );
        console.log(articleRequest);
        this.articleService.createArticle(articleRequest).subscribe({
            next: () => {
                this.router.navigate(['/articles']).then(r => {});
            },
        });
    }

    goBack() {
        this.router.navigate(['/articles']).then(r => {});
    }
}
