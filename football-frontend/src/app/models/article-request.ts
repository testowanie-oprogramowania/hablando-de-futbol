import { Editor } from './editor';
import { Category } from './category';

export class ArticleRequest {
    constructor(
        public title: string,
        public editorId: number,
        public publicationDate: Date,
        public content: string,
        public photoUrl: string,
        public categoryName: string,
        public id?: number
    ) {}

    public static fromForm(
        articleRequestRawFormValue: ArticleRequestRawFormValue
    ): ArticleRequest {
        console.log('editor: ');
        console.log(articleRequestRawFormValue.editor);
        return new ArticleRequest(
            articleRequestRawFormValue.title,
            articleRequestRawFormValue.editor.id!,
            articleRequestRawFormValue.publicationDate,
            articleRequestRawFormValue.content,
            articleRequestRawFormValue.photoUrl,
            articleRequestRawFormValue.category.name,
            articleRequestRawFormValue.id ?? undefined
        );
    }
}

export interface ArticleRequestRawFormValue {
    title: string;
    editor: Editor;
    publicationDate: Date;
    content: string;
    photoUrl: string;
    category: Category;
    id?: number;
}
