import { Component, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { CategoryEditDialogComponent } from '../category-edit-dialog/category-edit-dialog.component';
import { CategoryDeleteDialogComponent } from '../category-delete-dialog/category-delete-dialog.component';
import { Observable, tap } from 'rxjs';
import { CategoryService } from '../../services/category.service';
import { Router } from '@angular/router';
import { CategoryResource } from '../../models/category-resource';
import { CategoryRequest } from '../../models/category-request';

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
    categories$: Observable<CategoryResource[]>;

    constructor(
        public dialog: MatDialog,
        private readonly categoryService: CategoryService,
        private readonly router: Router
    ) {
        this.categories$ = categoryService.getAllCategories();
    }

    openEditDialog(category: CategoryResource) {
        const categoryCopy = new CategoryRequest(category.name);
        const dialogRef = this.dialog.open(CategoryEditDialogComponent, {
            data: categoryCopy,
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.categoryService
                    .updateCategory(category.id, result)
                    .subscribe({
                        next: () => {
                            this.categories$ =
                                this.categoryService.getAllCategories();
                        },
                    });
            } else {
                this.categories$ = this.categoryService.getAllCategories();
            }
        });
    }

    openDeleteDialog(categoryId: number) {
        const dialogRef = this.dialog.open(CategoryDeleteDialogComponent, {});
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.categoryService.deleteCategory(categoryId).subscribe({
                    next: () => {
                        this.categories$ =
                            this.categoryService.getAllCategories();
                    },
                });
            }
        });
    }

    openAddDialog() {
        const newCategory: CategoryRequest = {
            name: '',
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

    onCategoryClick(category: CategoryResource) {
        this.router.navigate(['/categories', category.id]);
    }
}
