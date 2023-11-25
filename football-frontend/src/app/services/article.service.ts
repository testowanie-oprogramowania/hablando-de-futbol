import { Injectable, ViewChild } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Article } from '../models/article';
import { MatPaginator } from '@angular/material/paginator';
import { map } from 'rxjs/operators';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  baseUrl = environment.apiBaseUrl;

  constructor(private readonly httpClient: HttpClient) {}
  public getArticles(request: {
    page: number;
    size: number;
  }): Observable<Article[]> {
    return this.httpClient
      .get<{ content: Article[] }>(this.baseUrl, { params: request })
      .pipe(map(response => response.content));
  }
}
