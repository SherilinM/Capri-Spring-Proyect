import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { EnumPipe } from './custom-pipes/enum-pipe';
import { AngularMaterialModule } from './angular-material.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { PlacePageComponent } from './components/place/place-page/place-page.component';
import { PlaceListComponent } from './components/place/place-list/place-list.component';
import { MatTabsModule } from '@angular/material/tabs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { PlaceCardComponent } from './components/place/place-page/place-card/place-card.component';
import { PlaceInstructionsComponent } from './components/place/place-page/place-instructions/place-instructions.component';
import { PlaceListItemComponent } from './components/place/place-list/place-list-item/place-list-item.component';
import { PlaceBrowserComponent } from './components/place/place-browser/place-browser.component';
import { PlaceSearchComponent } from './components/place/place-search/place-search.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddPlaceFormComponent } from './components/place/add-place-form/add-place-form.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserPageComponent } from './components/user/user-page/user-page.component';
import { UserDetailsComponent } from './components/user/user-details/user-details.component';
import { UserFavouritesComponent } from './components/user/user-favourites/user-favourites.component';
import { UserOwnPlaceComponent } from './components/user/user-own-place/user-own-place.component';
import { UserFormComponent } from './components/user/user-form/user-form.component';
import { DrawerComponent } from './components/drawer/drawer.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { HomePageComponent } from './components/home-page/home-page.component';
import { HomeSearchComponent } from './components/home-page/home-search/home-search.component';
import { FavouritesCarouselComponent } from './components/home-page/favourites-carousel/favourites-carousel.component';
import { MyProfileComponent } from './components/user/my-profile/my-profile.component';
import { AuthInterceptor } from './shared/okta/auth.interceptor';
import { FavouritesToggleComponent } from './components/place/favourites-toggle/favourites-toggle.component';
import { StarRatingComponent } from './components/rating/star-rating/star-rating.component';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PlaceAccordionComponent } from './components/place/place-accordion/place-accordion.component';
import { RatingCarouselGenericComponent } from './components/home-page/rating-carousel-generic/rating-carousel-generic.component';
import { ReviewListComponent } from './components/review/review-list/review-list.component';
import { ReviewListItemComponent } from './components/review/review-list-item/review-list-item.component';
import { ReviewFormComponent } from './components/review/review-form/review-form.component';
import { ConfirmComponent } from './components/dialogs/confirm/confirm.component';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    PlacePageComponent,
    PlaceListComponent,
    PlaceCardComponent,
    PlaceInstructionsComponent,
    PlaceListItemComponent,
    PlaceBrowserComponent,
    PlaceSearchComponent,
    AddPlaceFormComponent,
    UserPageComponent,
    UserDetailsComponent,
    UserFavouritesComponent,
    UserOwnPlaceComponent,
    UserFormComponent,
    DrawerComponent,
    HomePageComponent,
    HomeSearchComponent,
    FavouritesCarouselComponent,
    EnumPipe,
    MyProfileComponent,
    FavouritesToggleComponent,
    StarRatingComponent,
    PlaceAccordionComponent,
    RatingCarouselGenericComponent,
    ReviewListComponent,
    ReviewListItemComponent,
    ReviewFormComponent,
    ConfirmComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatTabsModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule,
    NgxPaginationModule,
    MDBBootstrapModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
