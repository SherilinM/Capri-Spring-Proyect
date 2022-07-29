import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { OktaAuth } from '@okta/okta-auth-js';
import { RatingService } from './../../../services/rating.service';
import { StarRatingComponent } from './../../rating/star-rating/star-rating.component';
import { ReviewService } from './../../../services/review.service';
import { UserDTO } from 'src/app/models/user-model';
import { PlaceDTO } from 'src/app/models/place.model';
import { ReviewResponse, ReviewDTO } from './../../../models/review-model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.css']
})
export class ReviewFormComponent implements OnInit {

  @Input()
  review!: ReviewResponse;
  @Input()
  place!: PlaceDTO;
  user!: UserDTO;

  ratingId!: number;
  formDisabled: boolean = false;

  @ViewChild(StarRatingComponent) starRatingComponent!: StarRatingComponent;

  formTitle: string = "Add new review"
  addOrEditButton: string = "Add"
  addForm: boolean = true;

  reviewForm: FormGroup;
  title!: FormControl;
  content!: FormControl;

  constructor(private snackBar: MatSnackBar,
    private reviewService: ReviewService,
    private ratingService: RatingService,
    private oktaAuth: OktaAuth,
    private userService: UserService,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: UserDTO) {
    this.title = new FormControl("", [Validators.required]),
      this.content = new FormControl("", [Validators.required]),
      this.reviewForm = new FormGroup({
        title: this.title,
        content: this.content
      })
  }

  ngOnInit(): void {
    this.addOrEditForm();
  }

  onSubmit(): void {
    if (this.addForm) {
      this.addReview();
    } else {
      this.editReview();
    }
  }

  addOrEditForm(): void {
    if (this.review != null) {
      this.formTitle = "Edit Review";
      this.addOrEditButton = "Edit";
      this.addForm = false;
      this.reviewForm.patchValue(this.review)
    } else {
      this.formTitle = "Add new Review";
      this.addOrEditButton = "Add";
      this.addForm = true;
    }
  }


  editReview(): void {
    const review: ReviewDTO = {
      id: this.review.id,
      title: this.title.value,
      content: this.content.value,
      placeId: this.place.id,
      userId: this.review.userId,
      ratingId: this.ratingId
    }
    this.reviewService.editReview(review).subscribe(result => {
      this.snackBar.open("Review Updated Successfully", "Close")
      window.location.reload();
    })
  }

  async addReview(): Promise<void> {
    if (this.ratingId != null) {
      await this.getUserByEmail();
      const review: ReviewDTO = {
        title: this.title.value,
        content: this.content.value,
        placeId: this.place.id,
        userId: this.user.id,
        ratingId: this.ratingId
      }
      this.disableForm();
      this.reviewService.addReview(review).subscribe(result => {
        this.snackBar.open("Review Added Successfully")
        window.location.reload();
      })
    } else {
      this.snackBar.open("Please Ensure You Rate The Place Before Submitting", "Close")
    }
  }

  setRatingId(ratingId: number) {
    this.ratingId = ratingId;
  }

  async getLoggedInEmail(): Promise<string> {
    let loggedInEmail: string = String((await this.oktaAuth.getUser()).preferred_username);
    return loggedInEmail;
  }

  async getUserByEmail(): Promise<void> {
    try {
      this.user = await this.userService.getUserByEmail(await this.getLoggedInEmail())
    } catch (error) {
      this.snackBar.open("Please ensure your details are correct before leaving a review", "close")
      this.router.navigateByUrl("/profile")
    }
  }

  disableForm(): void {
    this.reviewForm.disable();
    this.formDisabled = true;
  }

}
