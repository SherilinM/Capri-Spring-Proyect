import { PlaceDTO } from 'src/app/models/place.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  readonly baseUrl: string = "http://localhost:8080/api/v1/places"

  constructor(
    private http: HttpClient
  ) { }


  async getAllPlaces(): Promise<any> {
    return this.http.get<any>(this.baseUrl).toPromise();
  }

  async getPlaceById(id: number): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/" + id).toPromise();
  }

  async getPlacesByUserId(userId: number): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/user/" + userId).toPromise();
  }

  addPlace(placeDTO: PlaceDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, placeDTO);
  }

  deletePlace(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + "/delete/" + id)
  }

  editPlace(PlaceDTO: PlaceDTO): Observable<any> {
    return this.http.put(`${this.baseUrl + "/edit"}`, PlaceDTO)
  }

}
