import { EditorResource } from './editor-resource';
import { CategoryResource } from './category-resource';

export class ArticleRequest {
    constructor(
        public title: string,
        public editorId: number,
        public content: string,
        public photoUrl: string,
        public categoryName: string
    ) {}

    public static fromForm(
        articleRequestRawFormValue: ArticleRequestRawFormValue
    ): ArticleRequest {
        return new ArticleRequest(
            articleRequestRawFormValue.title,
            articleRequestRawFormValue.editor.id,
            articleRequestRawFormValue.content,
            articleRequestRawFormValue.photoUrl,
            articleRequestRawFormValue.category.name
        );
    }
}

export interface ArticleRequestRawFormValue {
    title: string;
    editor: EditorResource;
    content: string;
    photoUrl: string;
    category: CategoryResource;
}
