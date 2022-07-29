import { FavouriteDTO } from './../models/favourites-model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

type NewType = Observable<any>;

@Injectable({
  providedIn: 'root'
})
export class FavouritesService {

  readonly baseUrl: string = "http://localhost:8080/api/v1/favourites"

  constructor(
    private http: HttpClient
  ) { }


  getFavouritePlacesByUserId(userId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/userid/" + userId);
  }

  addToFavourites(favouriteDTO: FavouriteDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, favouriteDTO);
  }

  removeFromFavourites(favouriteDTO: FavouriteDTO): Observable<any> {
    return this.http.put(`${this.baseUrl}/remove`, favouriteDTO)
  }

  async getTop10FavouritedPlaces(): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/top10").toPromise();
  }

  isPlaceFavourited(favouriteDTO: FavouriteDTO): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/placeisfavourited`, favouriteDTO);
  }

}
