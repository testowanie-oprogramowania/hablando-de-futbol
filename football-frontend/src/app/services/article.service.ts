import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Article } from '../models/article';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: 'root',
})
export class ArticleService {
    articlesUrl = environment.apiBaseUrl + '/articles';

    constructor(private readonly httpClient: HttpClient) {}

    public createArticle(article: Article): Observable<any> {
        return this.httpClient.post<Article>(this.articlesUrl, article);
    }

    public getArticles(request: {
        page: number;
        size: number;
    }): Observable<Article[]> {
        return this.httpClient
            .get<{ content: Article[] }>(this.articlesUrl, { params: request })
            .pipe(map(response => response.content));
    }
}
