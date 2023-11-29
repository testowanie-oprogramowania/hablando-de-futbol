import { Category } from './category';
import { Comment } from './comment';
import { EditorResource } from './editor-resource';

export class ArticleResource {
    constructor(
        public id: number,
        public title: string,
        public editor: EditorResource,
        public createdDate: Date,
        public lastModifiedDate: Date,
        public content: string,
        public image: string,
        public category: Category,
        public comments: Comment[],
    ) {}
}
