import { Editor } from './editor';
import { Category } from './category';
import { Comment } from './comment';

export class ArticleResource {
    constructor(
        public title: string,
        public editor: Editor,
        public createdDate: Date,
        public content: string,
        public image: string,
        public category: Category,
        public comments: Comment[],
        public id?: number
    ) {}
}
