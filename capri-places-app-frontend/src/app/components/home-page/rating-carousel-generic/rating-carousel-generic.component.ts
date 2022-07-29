import { OktaAuth } from '@okta/okta-auth-js';
import { RatingService } from './../../../services/rating.service';
import { Component, Input, OnInit } from '@angular/core';
import { FavouritesService } from 'src/app/services/favourites.service';
import { UserService } from 'src/app/services/user.service';
import { PlaceDTO } from 'src/app/models/place.model';

@Component({
  selector: 'app-rating-carousel-generic',
  templateUrl: './rating-carousel-generic.component.html',
  styleUrls: ['./rating-carousel-generic.component.css']
})
export class RatingCarouselGenericComponent implements OnInit {

  @Input()
  userList: boolean = false;

  @Input()
  userId!: number;

  @Input()
  left!: boolean;

  @Input()
  ourOrYour!: string;

  loading: boolean = true;
  hasFavourites: boolean = true;

  constructor(private ratingService: RatingService,
    private userService: UserService) { }

  @Input()
  placeList!: PlaceDTO[];

  page: number = 0;

  async ngOnInit(): Promise<void> {
    await this.loadPlaces();
  }

  async getTop10Places(): Promise<void> {
    this.placeList = await this.ratingService.getTop10Places();
  }

  async getUsersTop10Places(): Promise<void> {
    this.placeList = await this.ratingService.getTop10PlacesForUser(this.userId)
  }

  async loadPlaces(): Promise<void> {
    if (this.userList) {
      try {
        await this.getUsersTop10Places();
        this.hasFavourites = true;
        this.loading = false;
      } catch (error) {
        this.hasFavourites = false;
      }

    } else {
      await this.getTop10Places();
      this.loading = false;
    }
  }



}
