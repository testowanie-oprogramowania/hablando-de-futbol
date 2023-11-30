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
                './article/create-update-article-form/create-update-article-form.component'
            ).then(c => c.CreateUpdateArticleFormComponent),
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
                './article/create-update-article-form/create-update-article-form.component'
            ).then(c => c.CreateUpdateArticleFormComponent),
    },
    {
        path: 'categories/:id',
        loadComponent: () =>
            import('./article/article-list/article-list.component').then(
                c => c.ArticleListComponent
            ),
    },
    {
        path: 'editors',
        loadComponent: () =>
            import('./editor/editor-list/editor-list.component').then(
                c => c.EditorListComponent
            ),
    }
];
