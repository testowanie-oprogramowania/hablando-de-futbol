import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
    FormBuilder,
    FormsModule,
    ReactiveFormsModule,
    Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { Observable, of } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import { TextInputFieldComponent } from '../../shared/text-input-field/text-input-field.component';
import { TextAreaFieldComponent } from '../../shared/text-area-field/text-area-field.component';
import { DatePickerFieldComponent } from '../../shared/date-picker-field/date-picker-field.component';
import { SelectFieldComponent } from '../../shared/select-field/select-field.component';
import { ArticleService } from '../../services/article.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { EditorService } from '../../services/editor.service';
import {
    ArticleRequest,
    ArticleRequestRawFormValue,
} from '../../models/article-request';
import { EditorResource } from '../../models/editor-resource';
import { CategoryResource } from '../../models/category-resource';
import { ShowPageComponent } from '../../shared/show-page/show-page.component';

@Component({
    selector: 'app-create-update-article-form',
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
        ShowPageComponent,
    ],
    templateUrl: './create-update-article-form.component.html',
    styleUrl: './create-update-article-form.component.scss',
})
export class CreateUpdateArticleFormComponent {
    isLoading: boolean = true;

    articleId: number | undefined;

    articleForm = this.formBuilder.group({
        title: ['', Validators.required],
        editor: [undefined as EditorResource | undefined, Validators.required],
        content: ['', Validators.required],
        photoUrl: ['', Validators.required],
        category: [
            undefined as CategoryResource | undefined,
            Validators.required,
        ],
    });

    categories$: Observable<CategoryResource[]>;
    categoryFormToShow = (category: CategoryResource) => category.name;

    editors$: Observable<EditorResource[]> = of();
    editorFormToShow = (editor: EditorResource) =>
        editor.name + ' ' + editor.surname;

    articleContentTextAreaRowsNumber = 15;

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

        const articleIdString = this.activatedRoute.snapshot.paramMap.get('id');
        this.articleId = articleIdString ? Number(articleIdString) : undefined;

        if (this.articleId) {
            this.fillFormWithArticle(this.articleId);
        }
    }

    submitForm = () => {
        const articleRequest = ArticleRequest.fromForm(
            this.articleForm.value as ArticleRequestRawFormValue
        );

        if (this.articleId) {
            this.articleService
                .updateArticle(this.articleId, articleRequest)
                .subscribe({
                    next: () => {
                        this.router.navigate(['/articles']).then(r => {});
                    },
                });
            return;
        }

        this.articleService.createArticle(articleRequest).subscribe({
            next: () => {
                this.router.navigate(['/articles']).then(r => {});
            },
        });
    };

    goBack() {
        this.router.navigate(['/articles']).then(r => {});
    }

    compareCategories(o1: CategoryResource, o2: CategoryResource): boolean {
        return o1.id === o2.id;
    }

    compareEditors(o1: EditorResource, o2: EditorResource): boolean {
        return o1.id === o2.id;
    }

    private fillFormWithArticle(articleId: number) {
        this.articleService.getArticle(articleId).subscribe({
            next: article => {
                this.articleForm.patchValue({
                    title: article.title,
                    editor: article.editor,
                    content: article.content,
                    photoUrl: article.image,
                    category: article.category,
                });
                this.isLoading = false;
            },
        });
    }
}
