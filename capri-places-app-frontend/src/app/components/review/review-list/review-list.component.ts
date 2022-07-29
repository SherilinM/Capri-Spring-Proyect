import { Router } from '@angular/router';
import { UserDTO } from 'src/app/models/user-model';
import { ReviewFormComponent } from './../review-form/review-form.component';
import { MatDialog } from '@angular/material/dialog';
import { ReviewService } from './../../../services/review.service';
import { PlaceDTO } from 'src/app/models/place.model';
import { ReviewResponse } from './../../../models/review-model';
import { Component, Input, OnInit } from '@angular/core';
import { resumeTransaction } from '@okta/okta-auth-js';

@Component({
  selector: 'app-review-list',
  templateUrl: './review-list.component.html',
  styleUrls: ['./review-list.component.css']
})
export class ReviewListComponent implements OnInit {

  @Input()
  reviewList!: ReviewResponse[];
  @Input()
  place!: PlaceDTO;
  @Input()
  user!: UserDTO;

  @Input()
  loggedInEmail!: string;

  page: number = 0;
  previouslyReviewed: boolean = true;

  constructor(private reviewService: ReviewService,
    private dialog: MatDialog,
    private router: Router) { }

  async ngOnInit(): Promise<void> {
    this.loadReviews();
    this.isPreviouslyReviewed();
  }

  loadReviews(): void {
    this.reviewService.getByPlaceId(this.place.id).subscribe(result => {
      this.reviewList = result;
    },
      error => {
        console.log("Is it an error")
      })
  }

  handlePageChange(event: number): void {
    this.page = event;
  }

  addReview(): void {
    this.router.navigateByUrl("/reviewform")
  }

  isPreviouslyReviewed(): void {
    this.reviewService.checkIfPreviouslyReviewed(this.user.id, this.place.id,).subscribe(result => {
      this.previouslyReviewed = result;
    })
  }

}
