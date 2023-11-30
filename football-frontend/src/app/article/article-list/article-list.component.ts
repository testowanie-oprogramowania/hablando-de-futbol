import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { NavbarComponent } from '../../navbar/navbar.component';
import { Observable, switchMap, tap } from 'rxjs';
import {
    MatPaginator,
    MatPaginatorModule,
    PageEvent,
} from '@angular/material/paginator';
import { ArticleService } from '../../services/article.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, ParamMap, Params, Router } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { ArticleResource } from '../../models/article-resource';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PaginatorRequestParams } from '../../models/paginator-request-params';

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
    categoryId: number | undefined;

    articles$!: Observable<ArticleResource[]>;
    dataLength = 0;

    @ViewChild(MatPaginator) readonly paginator!: MatPaginator;
    paginatorRequestParams: PaginatorRequestParams = new PaginatorRequestParams(
        0,
        8
    );
    paginatorLength = 10;
    headerTitle: string = '';

    constructor(
        private readonly articleService: ArticleService,
        private readonly categoryService: CategoryService,
        private readonly activatedRoute: ActivatedRoute,
        private readonly router: Router
    ) {}

    ngOnInit(): void {
        const categoryIdString =
            this.activatedRoute.snapshot.paramMap.get('id');
        this.categoryId = categoryIdString
            ? Number(categoryIdString)
            : undefined;

        this.activatedRoute.params.subscribe((params: Params) => {
            this.categoryId = params['id'] ? Number(params['id']) : undefined;
            this.articles$ = this.getData();
            this.setHeaderTitle();
        });
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
            .getArticles(this.paginatorRequestParams, this.categoryId)
            .pipe(
                tap({
                    next: articles => {
                        this.dataLength = articles.length;
                        this.setHeaderTitle();
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

    private setHeaderTitle(): void {
        if (this.categoryId) {
            this.categoryService.getCategory(this.categoryId).subscribe({
                next: category => {
                    this.headerTitle = category.category.name;
                },
            });
        } else {
            this.headerTitle = 'All articles';
        }
    }
}
