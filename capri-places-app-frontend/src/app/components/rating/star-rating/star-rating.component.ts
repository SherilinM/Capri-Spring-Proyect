import { UserDTO } from './../../../models/user-model';
import { UserService } from 'src/app/services/user.service';
import { OktaAuth } from '@okta/okta-auth-js';
import { RatingService } from './../../../services/rating.service';
import { PlaceDTO } from 'src/app/models/place.model';
import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { RatingDTO } from 'src/app/models/rating-model';

@Component({
  selector: 'app-star-rating',
  templateUrl: './star-rating.component.html',
  styleUrls: ['./star-rating.component.css']
})
export class StarRatingComponent implements OnInit {

  @Input()
  placeId!: number;
  loading: boolean = true;
  loggedInUser!: UserDTO;

  @Input()
  rating: number = 0;

  @Output() ratingOutput: EventEmitter<number> = new EventEmitter();

  @Input()
  isReadOnly: boolean = true;


  constructor(private ratingService: RatingService,
    private oktaAuth: OktaAuth,
    private userService: UserService) { }

  async ngOnInit(): Promise<void> {
    await this.getRating()
    this.loading = false;
  }

  async getRating(): Promise<void> {
    if (this.isReadOnly) {
      this.rating = await this.ratingService.getAverageRatingForPlace(this.placeId).catch(error => { });
      this.loading = false;
    }
  }

  async submitRating(): Promise<void> {
    if (this.rating != 0 || this.rating != null) {
      this.loggedInUser = await this.userService.getUserByEmail(await this.getLoggedInEmail())
      let ratingDTO: RatingDTO = {
        rating: this.rating,
        placeId: this.placeId,
        userId: this.loggedInUser.id
      }
      this.ratingService.ratePlace(ratingDTO).subscribe(result => {
        ratingDTO = result;
        this.ratingOutput.emit(ratingDTO.id);
      })
    }
  }

  private async getLoggedInEmail(): Promise<string> {
    return String((await this.oktaAuth.getUser()).preferred_username);
  }

}

