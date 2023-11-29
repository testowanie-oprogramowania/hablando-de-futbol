import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Editor} from "../models/editor";

@Injectable({
    providedIn: 'root',
})
export class EditorService {
    editorsUrl = environment.apiBaseUrl + '/editors';

    constructor(private readonly httpClient: HttpClient) {}

    public createArticle(editor: Editor): Observable<any> {
        return this.httpClient.post<Editor>(this.editorsUrl, editor);
    }

    public getEditors(request: {
        page: number;
        size: number;
    }): Observable<Editor[]> {
        return this.httpClient
            .get<{ content: Editor[] }>(this.editorsUrl, { params: request })
            .pipe(map(response => response.content));
    }
}
