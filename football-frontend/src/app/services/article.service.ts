import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { ArticleRequest } from '../models/article-request';
import { ArticleResource } from '../models/article-resource';

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

    public getArticles(request: {
        page: number;
        size: number;
    }): Observable<ArticleResource[]> {
        return this.httpClient
            .get<{ content: ArticleResource[] }>(this.articlesUrl, {
                params: request,
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
}
