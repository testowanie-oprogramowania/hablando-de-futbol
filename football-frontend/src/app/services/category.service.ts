import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category';
import { map } from 'rxjs';

export class CategoryService {
  constructor(private httpClient: HttpClient) {}
  private baseUrl = 'http://localhost:8080/api/v1/categories';

  getAllCategories() {
    return this.httpClient
      .get<{ content: Category[] }>(this.baseUrl)
      .pipe(map(response => response.content));
  }
}
