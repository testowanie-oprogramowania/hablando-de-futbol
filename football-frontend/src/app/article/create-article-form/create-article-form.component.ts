import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
    FormBuilder,
    FormGroup,
    FormsModule,
    ReactiveFormsModule,
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
        title: [''],
        editor: [''],
        publicationDate: [''],
        content: [''],
        photoUrl: [''],
        category: [''],
    });

    categories$: Observable<Category[]> = of();
    categoryFormToShow = (category: Category) => category.name;

    editors$: Observable<Editor[]> = of();
    editorFormToShow = (editor: Editor) => editor.name;

    constructor(private readonly formBuilder: FormBuilder) {}

    submitForm() {}
}
