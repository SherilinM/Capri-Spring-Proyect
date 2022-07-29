import { ReviewDTO } from './../models/review-model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  readonly baseUrl: string = "http://localhost:8080/api/v1/reviews"

  constructor(
    private http: HttpClient
  ) { }


  getByPlaceId(placeId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/place/" + placeId);
  }

  getByUserId(userId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/user/" + userId);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/" + id);
  }

  addReview(reviewDTO: ReviewDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, reviewDTO);
  }

  deleteReview(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + "/delete/" + id)
  }

  editReview(reviewDTO: ReviewDTO): Observable<any> {
    return this.http.put(`${this.baseUrl + "/edit"}`, reviewDTO)
  }

  checkIfPreviouslyReviewed(userId: number, placeId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/reviewed/" + userId + "/" + placeId);
  }
}
