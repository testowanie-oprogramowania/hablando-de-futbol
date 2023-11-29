import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { NavbarComponent } from '../../navbar/navbar.component';
import { Observable, tap } from 'rxjs';
import {
    MatPaginator,
    MatPaginatorModule,
    PageEvent,
} from '@angular/material/paginator';
import { ArticleService } from '../../services/article.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { ArticleResource } from '../../models/article-resource';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
    selector: 'app-article-list',
    standalone: true,
    imports: [
        CommonModule,
        NavbarComponent,
        MatPaginatorModule,
        MatCardModule,
        MatButtonModule,
        NgOptimizedImage,
        MatIconModule,
        MatProgressSpinnerModule,
    ],
    templateUrl: './article-list.component.html',
    styleUrl: './article-list.component.scss',
})
export class ArticleListComponent implements OnInit {
    articles$!: Observable<ArticleResource[]>;
    dataLength = 0;

    @ViewChild(MatPaginator) readonly paginator!: MatPaginator;
    paginatorLength = 10;
    paginatorPageSize = 10;
    paginatorPageIndex = 0;

    constructor(
        private readonly articleService: ArticleService,
        private readonly categoryService: CategoryService,
        private readonly router: Router
    ) {}

    ngOnInit(): void {
        this.articles$ = this.getData();
    }

    handlePageEvent(event: PageEvent): void {
        this.articles$ = this.getData();
    }

    private getData(): Observable<ArticleResource[]> {
        const request = {
            page: this.paginatorPageIndex,
            size: this.paginatorPageSize,
        };

        return this.articleService.getArticles(request).pipe(
            tap({
                next: articles => {
                    this.dataLength = articles.length;
                },
                error: err => console.log(err),
            })
        );
    }

    onReadArticle(id: number) {}

    onAddArticle($event: MouseEvent) {
        this.router.navigate(['articles/create']).then(r => {});
    }

    navigateToArticle(id: number) {
        this.router.navigate([`articles/${id}`]).then(r => {});
    }
}
