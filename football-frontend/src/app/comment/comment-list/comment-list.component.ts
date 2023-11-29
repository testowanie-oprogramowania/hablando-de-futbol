import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Comment } from '../../models/comment';
import { MatIconModule } from '@angular/material/icon';
import { MatExpansionModule } from '@angular/material/expansion';

@Component({
    selector: 'app-comment-list',
    standalone: true,
    imports: [
        CommonModule,
        MatTableModule,
        MatPaginatorModule,
        MatIconModule,
        MatExpansionModule,
    ],
    templateUrl: './comment-list.component.html',
    styleUrl: './comment-list.component.scss',
})
export class CommentListComponent {
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    displayedColumns = ['nickname', 'content', 'thumbsUp', 'thumbsDown'];
    dataLength = 0;

    private _comments = new MatTableDataSource<Comment>();

    @Input() set comments(data: Comment[]) {
        this._comments.data = data;
        this.dataLength = data.length;
        this._comments.paginator = this.paginator;
    }

    get comments(): MatTableDataSource<Comment> {
        return this._comments;
    }
}
