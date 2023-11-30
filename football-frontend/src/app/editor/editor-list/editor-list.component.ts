import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule, NgOptimizedImage } from '@angular/common';
import { ShowPageComponent } from '../../shared/show-page/show-page.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Observable, tap } from 'rxjs';
import { EditorResource } from '../../models/editor-resource';
import {
    MatPaginator,
    MatPaginatorModule,
    PageEvent,
} from '@angular/material/paginator';
import { PaginatorRequestParams } from '../../models/paginator-request-params';
import { Router } from '@angular/router';
import { EditorService } from '../../services/editor.service';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
    selector: 'app-editor-list',
    standalone: true,
    imports: [
        CommonModule,
        ShowPageComponent,
        MatButtonModule,
        MatIconModule,
        MatCardModule,
        MatProgressSpinnerModule,
        MatPaginatorModule,
        NgOptimizedImage,
    ],
    templateUrl: './editor-list.component.html',
    styleUrl: './editor-list.component.scss',
})
export class EditorListComponent implements OnInit {
    editors$!: Observable<EditorResource[]>;
    dataLength = 0;

    @ViewChild(MatPaginator) readonly paginator!: MatPaginator;
    paginatorRequestParams: PaginatorRequestParams = new PaginatorRequestParams(
        0,
        5
    );
    paginatorLength = 10;

    constructor(
        private readonly editorService: EditorService,
        private readonly router: Router
    ) {}

    ngOnInit(): void {
        this.editors$ = this.getData();
    }

    handlePageEvent(event: PageEvent): void {
        this.paginatorRequestParams = new PaginatorRequestParams(
            this.paginator.pageIndex,
            this.paginator.pageSize
        );
        this.editors$ = this.getData();
    }

    private getData(): Observable<EditorResource[]> {
        return this.editorService.getEditors(this.paginatorRequestParams).pipe(
            tap({
                next: articles => {
                    this.dataLength = articles.length;
                },
                error: err => console.log(err),
            })
        );
    }

    onEditEditor(id: number) {}

    onDeleteEditor(id: number) {}

    onAddEditor() {}
}
