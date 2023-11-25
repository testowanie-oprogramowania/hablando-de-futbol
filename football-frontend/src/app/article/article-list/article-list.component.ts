import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../navbar/navbar.component';
import { Observable, tap } from 'rxjs';
import { Article } from '../../models/article';
import {MatPaginator, MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import { ArticleService } from '../../services/article.service';
import {HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-article-list',
  standalone: true,
  imports: [CommonModule, NavbarComponent, MatPaginatorModule],
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
    this.articles$ = this.getData();
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
      // page: this.paginator.pageIndex,
      // size: this.paginator.pageSize,
      page: 0, size: 0
    };
    return this.articleService.getArticles(request).pipe(
      tap({
        next: articles => (this.dataLength = articles.length),
        error: err => console.log(err),
      })
    );
  }
}
