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
import { Router } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { ArticleResource } from '../../models/article-resource';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {PaginatorRequestParams} from "../../models/paginator-request-params";

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
    paginatorRequestParams: PaginatorRequestParams = new PaginatorRequestParams(
        0,
        6
    );
    paginatorLength = 10;

    constructor(
        private readonly articleService: ArticleService,
        private readonly categoryService: CategoryService,
        private readonly router: Router
    ) {}

    ngOnInit(): void {
        this.articles$ = this.getData();
    }

    handlePageEvent(event: PageEvent): void {
        this.paginatorRequestParams = new PaginatorRequestParams(
            this.paginator.pageIndex,
            this.paginator.pageSize
        );
        this.articles$ = this.getData();
    }

    private getData(): Observable<ArticleResource[]> {
        return this.articleService
            .getArticles(this.paginatorRequestParams)
            .pipe(
                tap({
                    next: articles => {
                        this.dataLength = articles.length;
                    },
                    error: err => console.log(err),
                })
            );
    }

    onAddArticle($event: MouseEvent) {
        this.router.navigate(['articles/create']).then(r => {});
    }

    navigateToArticle(id: number) {
        this.router.navigate([`articles/${id}`]).then(r => {});
    }
}
