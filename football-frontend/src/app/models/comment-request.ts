import { EditorResource } from './editor-resource';
import { CategoryResource } from './category-resource';
import { ArticleRequestRawFormValue } from './article-request';

export class CommentRequest {
    constructor(
        public nickname: string,
        public content: string
    ) {}

    public static fromForm(
        commentRequestRawFormValue: CommentRequestRawFormValue
    ): CommentRequest {
        return new CommentRequest(
            commentRequestRawFormValue.nickname,
            commentRequestRawFormValue.content
        );
    }
}

export interface CommentRequestRawFormValue {
    nickname: string;
    content: string;
}
