import { Routes } from '@angular/router';
import {ArticleListComponent} from "./article/article-list/article-list.component";

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'home',
    loadComponent: () =>
      import('./article/article-list/article-list.component').then(
        c => c.ArticleListComponent
      ),
  },
];
