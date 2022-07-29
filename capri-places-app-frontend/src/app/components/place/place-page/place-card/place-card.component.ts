import { OktaAuthStateService } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { RatingService } from './../../../../services/rating.service';
import { UserService } from './../../../../services/user.service';
import { UserDTO } from './../../../../models/user-model';
import { PlaceDTO } from '../../../../models/place.model';
import { Component, Input, OnInit } from '@angular/core';
import { RatingDTO } from 'src/app/models/rating-model';

@Component({
  selector: 'app-place-card',
  templateUrl: './place-card.component.html',
  styleUrls: ['./place-card.component.css']
})
export class PlaceCardComponent implements OnInit {

  @Input()
  place!: PlaceDTO;

  @Input()
  user!: UserDTO

  loggedInUser!: UserDTO
  usersRating!: number;
  ratingDTO!: RatingDTO;

  constructor(private userService: UserService,
    private ratingService: RatingService,
    private oktaAuth: OktaAuth,
    public authService: OktaAuthStateService) { }

  async ngOnInit(): Promise<void> {
    await this.getUser();
  }

  async getUser(): Promise<void> {
    this.loggedInUser = await this.userService.getUserByEmail(await this.getLoggedInEmail())
    this.getRating();
  }

  private getRating() {
    this.ratingDTO = {
      rating: 0,
      placeId: this.place.id,
      userId: this.loggedInUser.id
    };
    this.ratingService.getUsersRating(this.ratingDTO).subscribe(result => {
      this.usersRating = result;
    });
  }

  private async getLoggedInEmail(): Promise<string> {
    return String((await this.oktaAuth.getUser()).preferred_username);
  }

}
