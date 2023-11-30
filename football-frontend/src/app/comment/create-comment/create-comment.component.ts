import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators } from '@angular/forms';
import { TextInputFieldComponent } from '../../shared/text-input-field/text-input-field.component';
import { MatButtonModule } from '@angular/material/button';
import {
    CommentRequest,
    CommentRequestRawFormValue,
} from '../../models/comment-request';
import { ArticleService } from '../../services/article.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-create-comment',
    standalone: true,
    imports: [CommonModule, TextInputFieldComponent, MatButtonModule],
    templateUrl: './create-comment.component.html',
    styleUrl: './create-comment.component.scss',
})
export class CreateCommentComponent {
    @Input() articleId?: number;

    constructor(
        private readonly articleService: ArticleService,
        private readonly formBuilder: FormBuilder,
        private readonly router: Router
    ) {}

    commentForm = this.formBuilder.group({
        nickname: ['', Validators.required],
        content: ['', Validators.required],
    });

    onSubmit() {
        const articleRequest = CommentRequest.fromForm(
            this.commentForm.value as CommentRequestRawFormValue
        );

        this.articleService
            .createComment(this.articleId!, articleRequest)
            .subscribe({
                next: () => {
                    this.router
                        .navigateByUrl('', { skipLocationChange: true })
                        .then(r => {
                            this.router
                                .navigate(['/articles/' + this.articleId!])
                                .then(r => {});
                        });
                },
            });
    }
}
