import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { EditorResource } from '../models/editor-resource';
import { EditorRequest } from '../models/editor-request';
import { PaginatorRequestParams } from '../models/paginator-request-params';
import {ArticleResource} from "../models/article-resource";
import {ArticleRequest} from "../models/article-request";

@Injectable({
    providedIn: 'root',
})
export class EditorService {
    editorsUrl = environment.apiBaseUrl + '/editors';

    constructor(private readonly httpClient: HttpClient) {}

    public createEditor(editor: EditorRequest): Observable<any> {
        return this.httpClient.post<EditorRequest>(this.editorsUrl, editor);
    }

    public getEditor(id: number): Observable<EditorResource> {
        const url = this.editorsUrl + '/' + id;
        return this.httpClient.get<EditorResource>(url);
    }

    public getEditors(
        paginatorRequestParams: PaginatorRequestParams
    ): Observable<EditorResource[]> {
        const params = new HttpParams()
            .set('page', paginatorRequestParams.page.toString())
            .set('size', paginatorRequestParams.size.toString());

        return this.httpClient
            .get<{ content: EditorResource[] }>(this.editorsUrl, {
                params,
            })
            .pipe(map(response => response.content));
    }

    public updateEditor(
        editorId: number,
        editorRequest: EditorRequest
    ): Observable<void> {
        const url = this.editorsUrl + '/' + editorId;
        return this.httpClient.patch<void>(url, editorRequest);
    }

    public deleteEditor(editorId: number): Observable<void> {
        const url = this.editorsUrl + '/' + editorId;
        return this.httpClient.delete<void>(url);
    }
}
