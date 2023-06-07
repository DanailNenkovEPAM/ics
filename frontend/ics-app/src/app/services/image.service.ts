import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) { }

  analyzeImage(url: string): Observable<any> {
    return this.http.post('http://localhost:8080/images', { imageUrl: url });
  }
}