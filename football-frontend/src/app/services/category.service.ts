import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category';
import { map } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class CategoryService {
    constructor(private httpClient: HttpClient) {}
    private baseUrl = 'http://localhost:8080/api/v1/categories';

    getAllCategories() {
        return this.httpClient.get<Category[]>(this.baseUrl);
    }

    deleteCategory(category: Category) {
        return this.httpClient.delete<Category>(
            this.baseUrl + '/' + category.id
        );
    }

    addCategory(category: Category) {
        return this.httpClient.post<Category>(this.baseUrl, category);
    }

    updateCategory(category: Category) {
        return this.httpClient.put<Category>(
            this.baseUrl + '/' + category.id,
            category
        );
    }
}
