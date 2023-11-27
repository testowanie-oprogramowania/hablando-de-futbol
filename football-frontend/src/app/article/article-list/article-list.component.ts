import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../navbar/navbar.component';
import { Observable, of, tap } from 'rxjs';
import { Article } from '../../models/article';
import {
    MatPaginator,
    MatPaginatorModule,
    PageEvent,
} from '@angular/material/paginator';
import { ArticleService } from '../../services/article.service';
import { HttpClientModule } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { Editor } from '../../models/editor';
import { Category } from '../../models/category';

@Component({
    selector: 'app-article-list',
    standalone: true,
    imports: [
        CommonModule,
        NavbarComponent,
        MatPaginatorModule,
        MatCardModule,
        MatButtonModule,
    ],
    templateUrl: './article-list.component.html',
    styleUrl: './article-list.component.scss',
})
export class ArticleListComponent {
    articles$: Observable<Article[]>;
    dataLength = 0;

    @ViewChild(MatPaginator) readonly paginator!: MatPaginator;
    paginatorLength = 100;
    paginatorPageSize = 10;
    paginatorPageIndex = 0;

    constructor(private readonly articleService: ArticleService) {
        // this.articles$ = this.getData();
        this.articles$ = of(Array(10).fill(
            new Article(
                'tytul',
                new Editor(
                    'Jan',
                    'Kowalski',
                    'https://avatars.githubusercontent.com/u/22162049?v=4'
                ),
                new Date('25-11-2023'),
                'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an',
                'https://material.angular.io/assets/img/examples/shiba2.jpg',
                new Category(
                    'Kategoria',
                    []
                ),
                [],
                1
            ),
        ));
    }

    handlePageEvent(event: PageEvent): void {
        const request = {
            page: event.pageIndex,
            size: event.pageSize,
        };
        this.articles$ = this.articleService.getArticles(request);
    }

    private getData(): Observable<Article[]> {
        const request = {
            page: this.paginatorPageIndex,
            size: this.paginatorPageSize,
        };
        return this.articleService.getArticles(request).pipe(
            tap({
                next: articles => (this.dataLength = articles.length),
                error: err => console.log(err),
            })
        );
    }

    onReadArticle(id: number) {}
}