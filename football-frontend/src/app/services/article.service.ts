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

    public createArticle(articleRequest: ArticleRequest): Observable<any> {
        return this.httpClient.post<ArticleRequest>(
            this.articlesUrl,
            articleRequest
        );
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
}
