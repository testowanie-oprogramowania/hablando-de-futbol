import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { EditorResource } from '../models/editor-resource';
import { EditorRequest } from '../models/editor-request';

@Injectable({
    providedIn: 'root',
})
export class EditorService {
    editorsUrl = environment.apiBaseUrl + '/editors';

    constructor(private readonly httpClient: HttpClient) {}

    public createArticle(editor: EditorRequest): Observable<any> {
        return this.httpClient.post<EditorRequest>(this.editorsUrl, editor);
    }

    public getEditors(request: {
        page: number;
        size: number;
    }): Observable<EditorResource[]> {
        return this.httpClient
            .get<{ content: EditorResource[] }>(this.editorsUrl, {
                params: request,
            })
            .pipe(map(response => response.content));
    }
}
