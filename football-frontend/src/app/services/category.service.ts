import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category';
import { Injectable } from '@angular/core';
import { CategoryResource } from '../models/category-resource';
import { CategoryRequest } from '../models/category-request';

@Injectable({
    providedIn: 'root',
})
export class CategoryService {
    constructor(private httpClient: HttpClient) {}
    private baseUrl = 'http://localhost:8080/api/v1/categories';

    getAllCategories() {
        return this.httpClient.get<CategoryResource[]>(this.baseUrl);
    }

    deleteCategory(categoryId: number) {
        return this.httpClient.delete<CategoryResource>(
            this.baseUrl + '/' + categoryId
        );
    }

    addCategory(categoryRequest: CategoryRequest) {
        return this.httpClient.post<CategoryRequest>(
            this.baseUrl,
            categoryRequest
        );
    }

    updateCategory(categoryId: number, categoryRequest: CategoryRequest) {
        return this.httpClient.patch<CategoryRequest>(
            this.baseUrl + '/' + categoryId,
            categoryRequest
        );
    }
}
