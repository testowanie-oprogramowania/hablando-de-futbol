import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { Comment } from '../../models/comment';
import { MatIconModule } from '@angular/material/icon';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatButtonModule } from '@angular/material/button';
import { ArticleService } from '../../services/article.service';
import { CreateCommentComponent } from '../create-comment/create-comment.component';

@Component({
    selector: 'app-comment-list',
    standalone: true,
    imports: [
        CommonModule,
        MatTableModule,
        MatPaginatorModule,
        MatIconModule,
        MatExpansionModule,
        MatButtonModule,
        CreateCommentComponent,
    ],
    templateUrl: './comment-list.component.html',
    styleUrl: './comment-list.component.scss',
})
export class CommentListComponent {
    @ViewChild('paginator') paginator!: MatPaginator;
    displayedColumns = ['nickname', 'content', 'thumbsUp', 'thumbsDown'];
    dataLength = 0;
    _comments = new MatTableDataSource<Comment>();
    constructor(private readonly articleService: ArticleService) {}
    @Input() set comments(data: Comment[]) {
        this._comments.data = data;
        this.dataLength = data.length;
    }
    @Input() articleId!: number;

    get comments(): MatTableDataSource<Comment> {
        return this._comments;
    }

    ngAfterViewInit() {
        this._comments.paginator = this.paginator;
    }

    onThumbUp(comment: Comment) {
        if (comment.isLikedByUser) {
            this.articleService
                .removeLikeFromTheComment(this.articleId, comment.id)
                .subscribe();
            comment.isLikedByUser = false;
            comment.thumbsUp--;
            this._comments.data = [...this._comments.data];
            return;
        }
        this.articleService
            .addLikeToTheComment(this.articleId, comment.id)
            .subscribe();
        comment.isLikedByUser = true;
        comment.thumbsUp++;
        this._comments.data = [...this._comments.data];
    }

    onThumbDown(comment: Comment) {
        if (comment.isDislikedByUser) {
            this.articleService
                .removeDislikeFromTheComment(this.articleId, comment.id)
                .subscribe();
            comment.isDislikedByUser = false;
            comment.thumbsDown--;
            this._comments.data = [...this._comments.data];
            return;
        }
        this.articleService
            .addDislikeToTheComment(this.articleId, comment.id)
            .subscribe();
        comment.isDislikedByUser = true;
        comment.thumbsDown++;
        this._comments.data = [...this._comments.data];
    }
}
