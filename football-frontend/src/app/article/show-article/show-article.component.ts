import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatButtonModule} from "@angular/material/button";
import {SelectFieldComponent} from "../../shared/select-field/select-field.component";
import {ShowPageComponent} from "../../shared/show-page/show-page.component";
import {TextAreaFieldComponent} from "../../shared/text-area-field/text-area-field.component";
import {TextInputFieldComponent} from "../../shared/text-input-field/text-input-field.component";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {ArticleResource} from "../../models/article-resource";
import {ArticleService} from "../../services/article.service";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@Component({
    selector: 'app-show-article',
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        SelectFieldComponent,
        ShowPageComponent,
        TextAreaFieldComponent,
        TextInputFieldComponent,
        MatProgressSpinnerModule,
    ],
    templateUrl: './show-article.component.html',
    styleUrl: './show-article.component.scss',
})
export class ShowArticleComponent {
    articleId: number;
    article$: Observable<ArticleResource>;
    constructor(
        private readonly articleService: ArticleService,
        private readonly activatedRoute: ActivatedRoute
    ) {
        this.articleId = Number(
            this.activatedRoute.snapshot.paramMap.get('id')
        );
        this.article$ = articleService.getArticle(this.articleId);
    }
}
