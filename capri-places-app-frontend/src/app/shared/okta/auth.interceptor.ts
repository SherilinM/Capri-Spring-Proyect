import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { OktaAuth } from '@okta/okta-auth-js';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private oktaAuth: OktaAuth) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this.handleAccess(request, next);
  }

  private handleAccess(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Only add an access token to allowed origins
    const allowedOrigins = [
      'http://localhost:8080/api/v1/users/profile',
      'http://localhost:8080/api/v1/favourites/add',
      'http://localhost:8080/api/v1/favourites/remove',
      'http://localhost:8080/api/v1/favourites/placeisfavourited',
      'http://localhost:8080/api/v1/ratings/rateplace',
      'http://localhost:8080/api/v1/ratings/usersrating',
      'http://localhost:8080/api/v1/users/edit',
      'http://localhost:8080/api/v1/places/add',
      'http://localhost:8080/api/v1/places/edit',
      'http://localhost:8080/api/v1/places/delete',
      'http://localhost:8080/api/v1/reviews/add',
      'http://localhost:8080/api/v1/reviews/edit',
      'http://localhost:8080/api/v1/reviews/delete'
    ];
    if (allowedOrigins.some(url => request.urlWithParams.includes(url))) {
      const accessToken = this.oktaAuth.getAccessToken();
      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + accessToken
        }
      });
    }
    return next.handle(request);
  }
}
