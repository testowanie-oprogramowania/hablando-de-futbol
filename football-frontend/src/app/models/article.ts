import { Editor } from './editor';
import { Category } from './category';
import { Comment } from './comment';

export class Article {
    constructor(
        public title: string,
        public editor: Editor,
        public publicationDate: Date,
        public content: string,
        public photoUrl: string,
        public category: Category,
        public comments: Comment[],
        public id?: number
    ) {}

    public static fromForm(articleRawFormValue: ArticleRawFormValue): Article {
        return new Article(
            articleRawFormValue.title,
            articleRawFormValue.editor,
            articleRawFormValue.publicationDate,
            articleRawFormValue.content,
            articleRawFormValue.photoUrl,
            articleRawFormValue.category,
            [],
            articleRawFormValue.id ?? undefined
        );
    }
}

export interface ArticleRawFormValue {
    title: string;
    editor: Editor;
    publicationDate: Date;
    content: string;
    photoUrl: string;
    category: Category;
    id?: number;
}
