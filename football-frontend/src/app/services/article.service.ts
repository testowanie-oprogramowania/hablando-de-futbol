import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ArticleRequest } from '../models/article-request';
import { ArticleResource } from '../models/article-resource';
import { PaginatorRequestParams } from '../models/paginator-request-params';

@Injectable({
    providedIn: 'root',
})
export class ArticleService {
    articlesUrl = environment.apiBaseUrl + '/articles';

    constructor(private readonly httpClient: HttpClient) {}

    public createArticle(articleRequest: ArticleRequest): Observable<void> {
        return this.httpClient.post<void>(this.articlesUrl, articleRequest);
    }

    public getArticle(id: number): Observable<ArticleResource> {
        const url = this.articlesUrl + '/' + id;
        return this.httpClient.get<ArticleResource>(url);
    }

    public getArticles(
        paginatorRequestParams: PaginatorRequestParams,
        categoryId?: number
    ): Observable<ArticleResource[]> {
        const request = {
            page: paginatorRequestParams.page.toString(),
            size: paginatorRequestParams.size.toString(),
            category: categoryId ? categoryId.toString() : undefined,
        };

        const params = new HttpParams()
            .set('page', paginatorRequestParams.page.toString())
            .set('size', paginatorRequestParams.size.toString())
            .set('category', categoryId ? categoryId.toString() : '');

        return this.httpClient
            .get<{ content: ArticleResource[] }>(this.articlesUrl, {
                params,
            })
            .pipe(map(response => response.content));
    }

    public updateArticle(
        articleId: number,
        articleRequest: ArticleRequest
    ): Observable<void> {
        const url = this.articlesUrl + '/' + articleId;
        return this.httpClient.patch<void>(url, articleRequest);
    }

    public deleteArticle(articleId: number): Observable<void> {
        const url = this.articlesUrl + '/' + articleId;
        return this.httpClient.delete<void>(url);
    }

    public addLikeToTheComment(
        articleId: number,
        commentId: number
    ): Observable<void> {
        const url =
            this.articlesUrl +
            '/' +
            articleId +
            '/comments/' +
            commentId +
            '/like/add';
        console.log(url);
        return this.httpClient.post<void>(url, {});
    }

    public addDislikeToTheComment(
        articleId: number,
        commentId: number
    ): Observable<void> {
        const url =
            this.articlesUrl +
            '/' +
            articleId +
            '/comments/' +
            commentId +
            '/dislike/add';
        console.log(url);
        return this.httpClient.post<void>(url, {});
    }
}
