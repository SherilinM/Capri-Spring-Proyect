import { AddPlaceFormComponent } from './components/place/add-place-form/add-place-form.component';
import { MyProfileComponent } from './components/user/my-profile/my-profile.component';
import { PlaceBrowserComponent } from './components/place/place-browser/place-browser.component';
import { PlacePageComponent } from './components/place/place-page/place-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserPageComponent } from './components/user/user-page/user-page.component';
import { OktaAuth } from '@okta/okta-auth-js';
import { OktaAuthGuard, OktaAuthModule, OktaCallbackComponent, OKTA_CONFIG } from '@okta/okta-angular';
import { HomePageComponent } from './components/home-page/home-page.component';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
// import { AuthInterceptor } from './shared/okta/auth.interceptor';

const oktaConfig = {
  issuer: 'https://dev-1472640.okta.com/oauth2/default',
  clientId: '0oa2xmwgl6FfE7mbI5d7',
  redirectUri: '/login/callback',
  scopes: ['openid', 'profile']
};

const oktaAuth = new OktaAuth(oktaConfig);

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'home',
    component: HomePageComponent
  },
  {
    path: 'login/callback',
    component: OktaCallbackComponent
  },
  {
    path: 'profile',
    component: MyProfileComponent,
    canActivate: [OktaAuthGuard]
  },
  {
    path: 'places/:placeId',
    component: PlacePageComponent
  },
  {
    path: 'users/:userId',
    component: UserPageComponent
  },
  {
    path: 'places',
    component: PlaceBrowserComponent
  },
  {
    path: 'addplace',
    component: AddPlaceFormComponent,
    canActivate: [OktaAuthGuard]
  },
  {
    path: '**',
    component: HomePageComponent
  }
];

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    OktaAuthModule,
    RouterModule.forRoot(routes)],
  providers: [
    { provide: OKTA_CONFIG, useValue: { oktaAuth } }
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
