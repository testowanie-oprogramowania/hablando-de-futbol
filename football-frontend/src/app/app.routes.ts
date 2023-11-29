import { Routes } from '@angular/router';
import { ArticleListComponent } from './article/article-list/article-list.component';

export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    {
        path: 'home',
        loadComponent: () =>
            import('./article/article-list/article-list.component').then(
                c => c.ArticleListComponent
            ),
    },
    {
        path: 'articles',
        loadComponent: () =>
            import('./article/article-list/article-list.component').then(
                c => c.ArticleListComponent
            ),
    },
    {
        path: 'articles/create',
        loadComponent: () =>
            import(
                './article/create-article-form/create-article-form.component'
            ).then(c => c.CreateArticleFormComponent),
    },
    {
        path: 'articles/:id',
        loadComponent: () =>
            import('./article/show-article/show-article.component').then(
                c => c.ShowArticleComponent
            ),
    },
    {
        path: 'articles/:id/edit',
        loadComponent: () =>
            import(
                './article/create-article-form/create-article-form.component'
            ).then(c => c.CreateArticleFormComponent),
    },
    {
        path: 'categories/:id',
        loadComponent: () =>
            import('./article/article-list/article-list.component').then(
                c => c.ArticleListComponent
            ),
    },
];
