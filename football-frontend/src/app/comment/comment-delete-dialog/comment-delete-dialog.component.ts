import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import {
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogTitle,
} from '@angular/material/dialog';

@Component({
    selector: 'app-comment-delete-dialog',
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatDialogActions,
        MatDialogContent,
        MatDialogTitle,
        MatDialogClose,
    ],
    templateUrl: './comment-delete-dialog.component.html',
    styleUrl: './comment-delete-dialog.component.scss',
})
export class CommentDeleteDialogComponent {}
