import { Component } from '@angular/core';
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
  categories: Category[] = [
    { id: 1, name: 'Kategoria 1', articles: [] },
    { id: 3, name: 'Kategoria 2', articles: [] },
  ];
  constructor(public dialog: MatDialog) {}
  openEditDialog(category: Category) {
    const dialogRef = this.dialog.open(CategoryEditDialogComponent, {
      data: category,
    });

    // dialogRef.afterClosed().subscribe((result) => {
    //   console.log(`Dialog result: ${result}`);
    // });
  }

  openDeleteDialog(category: Category) {
    const dialogRef = this.dialog.open(CategoryDeleteDialogComponent, {});
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // HTTP DELETE
      }
    });
  }
}
