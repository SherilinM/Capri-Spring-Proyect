import { RatingDTO } from './../models/rating-model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  readonly baseUrl: string = "http://localhost:8080/api/v1/ratings"

  constructor(
    private http: HttpClient
  ) { }

  getByUserId(userId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/" + userId);
  }

  checkIfPreviouslyRated(placeId: number, userId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/" + placeId + "/" + userId)
  }

  async getAverageRatingForPlace(placeId: any): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/place/" + placeId).toPromise();
  }

  async getTop10Places(): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/top10places").toPromise();
  }

  async getTop10PlacesForUser(userId: number): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/top10places/" + userId).toPromise();
  }

  ratePlace(ratingDTO: RatingDTO): Observable<any> {
    return this.http.put(`${this.baseUrl}/rateplace`, ratingDTO)
  }

  getUsersRating(ratingDTO: RatingDTO): Observable<any> {
    return this.http.put(`${this.baseUrl}/usersrating`, ratingDTO)
  }
}
