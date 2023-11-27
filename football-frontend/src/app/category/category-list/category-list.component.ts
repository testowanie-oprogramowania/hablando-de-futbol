import { Component, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { Category } from '../../models/category';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { CategoryEditDialogComponent } from '../category-edit-dialog/category-edit-dialog.component';
import { CategoryDeleteDialogComponent } from '../category-delete-dialog/category-delete-dialog.component';
import { Observable, tap } from 'rxjs';
import { CategoryService } from '../../services/category.service';

@Component({
    selector: 'app-category-list',
    standalone: true,
    imports: [
        CommonModule,
        MatExpansionModule,
        MatListModule,
        MatMenuModule,
        MatIconModule,
        MatButtonModule,
    ],
    templateUrl: './category-list.component.html',
    styleUrl: './category-list.component.scss',
})
export class CategoryListComponent {
    categories$: Observable<Category[]>;

    constructor(
        public dialog: MatDialog,
        private readonly categoryService: CategoryService
    ) {
        this.categories$ = categoryService.getAllCategories();
    }

    openEditDialog(category: Category) {
        const dialogRef = this.dialog.open(CategoryEditDialogComponent, {
            data: category,
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.categoryService.updateCategory(result).subscribe({
                    next: () => {
                        this.categories$ =
                            this.categoryService.getAllCategories();
                    },
                });
            }
        });
    }

    openDeleteDialog(category: Category) {
        const dialogRef = this.dialog.open(CategoryDeleteDialogComponent, {});
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.categoryService.deleteCategory(category).subscribe({
                    next: () => {
                        this.categories$ =
                            this.categoryService.getAllCategories();
                    },
                });
            }
        });
    }

    openAddDialog() {
        const newCategory: Category = {
            name: '',
            articles: [],
        };
        const dialogRef = this.dialog.open(CategoryEditDialogComponent, {
            data: newCategory,
        });

        dialogRef.afterClosed().subscribe(newCategory => {
            if (newCategory) {
                this.categoryService.addCategory(newCategory).subscribe({
                    next: () => {
                        this.categories$ =
                            this.categoryService.getAllCategories();
                    },
                });
            }
        });
    }
}
