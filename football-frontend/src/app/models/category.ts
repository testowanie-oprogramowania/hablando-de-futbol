import {Article} from "./article";

export class Category {
    id?: number;
    name: string;
    articles: Article[];

    constructor(name: string, articles: Article[], id?: number) {
        this.id = id;
        this.name = name;
        this.articles = articles;
    }
}
