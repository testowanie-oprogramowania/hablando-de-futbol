import { Editor } from './editor';
import { Category } from './category';
import { Comment } from './comment';

export class Article {
  id?: number;
  title: string;
  editor: Editor;
  publicationDate: Date;
  content: string;
  photoUrl: string;
  category: Category;
  comments: Comment[];

  constructor(
    title: string,
    editor: Editor,
    publicationDate: Date,
    content: string,
    photoUrl: string,
    category: Category,
    comments: Comment[],
    id?: number
  ) {
    this.id = id;
    this.title = title;
    this.editor = editor;
    this.publicationDate = publicationDate;
    this.content = content;
    this.photoUrl = photoUrl;
    this.category = category;
    this.comments = comments;
  }

  public static fromForms(articleRawFormValue: ArticleRawFormValue): Article {
    return new Article(
      articleRawFormValue.title,
      articleRawFormValue.editor,
      articleRawFormValue.publicationDate,
      articleRawFormValue.content,
      articleRawFormValue.photoUrl,
      articleRawFormValue.category,
      [],
      undefined
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
}
