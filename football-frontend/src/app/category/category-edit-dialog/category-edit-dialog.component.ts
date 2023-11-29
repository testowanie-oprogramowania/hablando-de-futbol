import { Component, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
    MAT_DIALOG_DATA,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent,
    MatDialogRef,
    MatDialogTitle,
} from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { CategoryRequest } from '../../models/category-request';

@Component({
    selector: 'app-category-edit-dialog',
    standalone: true,
    imports: [
        CommonModule,
        MatDialogContent,
        MatInputModule,
        FormsModule,
        MatDialogClose,
        MatButtonModule,
        MatDialogActions,
        MatDialogTitle,
    ],
    templateUrl: './category-edit-dialog.component.html',
    styleUrl: './category-edit-dialog.component.scss',
})
export class CategoryEditDialogComponent {
    constructor(
        public dialogRef: MatDialogRef<CategoryEditDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: CategoryRequest
    ) {}

    onCancelClick(): void {
        this.dialogRef.close();
    }

    onSaveClick(): void {
        this.dialogRef.close(this.data);
    }
}
