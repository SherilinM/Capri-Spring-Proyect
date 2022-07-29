import { UserDTO } from './../models/user-model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  readonly baseUrl: string = "http://localhost:8080/api/v1/users"

  constructor(
    private http: HttpClient
  ) { }

  async getUserById(id: number): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/" + id).toPromise();
  }

  addUser(userDTO: UserDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}`, userDTO);
  }

  editUser(UserDTO: UserDTO): Observable<any> {
    return this.http.put(`${this.baseUrl + "/edit"}`, UserDTO)
  }

  userVerified(userId: number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/" + userId + "/verify")
  }

  loadProfilePage(): Observable<any> {
    return  this.http.get<any>(this.baseUrl + "/profile")
  }

  async getUserByEmail(email: string): Promise<any> {
    return this.http.get<any>(this.baseUrl + "/email/" + email).toPromise();
  }

}
