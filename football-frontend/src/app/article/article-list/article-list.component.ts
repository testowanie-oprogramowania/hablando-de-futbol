import {Component, OnInit, ViewChild} from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { NavbarComponent } from '../../navbar/navbar.component';
import { Observable, of, tap } from 'rxjs';
import {
    MatPaginator,
    MatPaginatorModule,
    PageEvent,
} from '@angular/material/paginator';
import { ArticleService } from '../../services/article.service';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { ArticleResource } from '../../models/article-resource';

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
    ],
    templateUrl: './article-list.component.html',
    styleUrl: './article-list.component.scss',
})
export class ArticleListComponent implements OnInit {

    articles$!: Observable<ArticleResource[]>;
    dataLength = 0;

    @ViewChild(MatPaginator) readonly paginator!: MatPaginator;
    paginatorLength = 10;
    paginatorPageSize = 5;
    paginatorPageIndex = 0;

    constructor(
        private readonly articleService: ArticleService,
        private readonly categoryService: CategoryService,
        private readonly router: Router,
        private readonly route: ActivatedRoute
    ) {

        // this.articles$ = of(
        //     Array(10).fill(
        //         new Article(
        //             'tytul',
        //             new Editor(
        //                 'Jan',
        //                 'Kowalski',
        //                 'https://avatars.githubusercontent.com/u/22162049?v=4'
        //             ),
        //             new Date('25-11-2023'),
        //             "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an",
        //             'https://material.angular.io/assets/img/examples/shiba2.jpg',
        //             new Category('Kategoria', []),
        //             [],
        //             1
        //         )
        //     )
        // );
    }

    ngOnInit(): void {
        this.articles$ = this.getData();
    }

    handlePageEvent(event: PageEvent): void {
        // this.isLoading = true;
        // const request = {
        //     page: event.pageIndex,
        //     size: event.pageSize,
        // };
        // this.articles$ = this.articleService.getArticles(request).pipe(
        //     tap({
        //         complete: () => (this.isLoading = false),
        //     })
        // );
        this.articles$ = this.getData();
    }

    private getData(): Observable<ArticleResource[]> {
        const request = {
            page: this.paginatorPageIndex,
            size: this.paginatorPageSize,
        };

        console.log('bbb');
        return this.articleService.getArticles(request).pipe(
            tap({
                next: articles => {
                    this.dataLength = articles.length;
                    console.log('aaa');
                },
                error: err => console.log(err)
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
