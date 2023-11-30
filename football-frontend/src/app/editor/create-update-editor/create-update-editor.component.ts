import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators } from '@angular/forms';
import { EditorResource } from '../../models/editor-resource';
import { CategoryResource } from '../../models/category-resource';
import { Observable, of } from 'rxjs';
import { ArticleService } from '../../services/article.service';
import { CategoryService } from '../../services/category.service';
import { EditorService } from '../../services/editor.service';
import { ActivatedRoute, Router } from '@angular/router';
import {
    ArticleRequest,
    ArticleRequestRawFormValue,
} from '../../models/article-request';
import {
    EditorRequest,
    EditorRequestRawFormValue,
} from '../../models/editor-request';
import {ShowPageComponent} from "../../shared/show-page/show-page.component";
import {TextInputFieldComponent} from "../../shared/text-input-field/text-input-field.component";
import {MatButtonModule} from "@angular/material/button";

@Component({
    selector: 'app-create-update-editor',
    standalone: true,
    imports: [
        CommonModule,
        ShowPageComponent,
        TextInputFieldComponent,
        MatButtonModule,
    ],
    templateUrl: './create-update-editor.component.html',
    styleUrl: './create-update-editor.component.scss',
})
export class CreateUpdateEditorComponent {
    isLoading: boolean = true;
    headerTitle: string = '';
    submitButtonTitle: string = 'Submit';

    editorId: number | undefined;

    editorForm = this.formBuilder.group({
        name: ['', Validators.required],
        surname: ['', Validators.required],
        photoUrl: ['', Validators.required],
    });

    constructor(
        private readonly editorService: EditorService,
        private readonly activatedRoute: ActivatedRoute,
        private readonly formBuilder: FormBuilder,
        private readonly router: Router
    ) {
        const editorIdString = this.activatedRoute.snapshot.paramMap.get('id');
        this.editorId = editorIdString ? Number(editorIdString) : undefined;

        if (this.editorId) {
            this.fillPageWithEditor(this.editorId);
            this.headerTitle = 'Edit editor';
            this.submitButtonTitle = 'Update';
        } else {
            this.isLoading = false;
            this.headerTitle = 'Create editor';
            this.submitButtonTitle = 'Create';
        }
    }

    submitForm = () => {
        const editorRequest = EditorRequest.fromForm(
            this.editorForm.value as EditorRequestRawFormValue
        );

        if (this.editorId) {
            this.editorService
                .updateEditor(this.editorId, editorRequest)
                .subscribe({
                    next: () => {
                        this.goBack();
                    },
                });
            return;
        }

        this.editorService.createEditor(editorRequest).subscribe({
            next: () => {
                this.goBack();
            },
        });
    };

    goBack() {
        this.router.navigate(['/editors']).then(r => {});
    }

    private fillPageWithEditor(editorId: number) {
        this.editorService.getEditor(editorId).subscribe({
            next: editor => {
                this.editorForm.patchValue({
                    name: editor.name,
                    surname: editor.surname,
                    photoUrl: editor.image,
                });
                this.isLoading = false;
            },
        });
    }
}
