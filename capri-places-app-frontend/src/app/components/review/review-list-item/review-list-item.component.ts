import { ConfirmComponent } from './../../dialogs/confirm/confirm.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReviewService } from './../../../services/review.service';
import { PlaceDTO } from 'src/app/models/place.model';
import { ReviewResponse } from './../../../models/review-model';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-review-list-item',
  templateUrl: './review-list-item.component.html',
  styleUrls: ['./review-list-item.component.css']
})
export class ReviewListItemComponent implements OnInit {

  @Input()
  review!: ReviewResponse

  @Input()
  loggedInEmail: string | undefined;

  @Input()
  place!: PlaceDTO;

  ownReview: boolean = false;
  editing: boolean = false;
  confirmed: string = "no";

  constructor(private reviewService: ReviewService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog) { }

  ngOnInit(): void {
    this.isOwnReview();
  }

  isOwnReview(): void {
    if (this.loggedInEmail?.trim().toLowerCase() == this.review.email.trim().toLowerCase()) {
      this.ownReview = true;
    }
  }

  loadForm(): void {
    this.editing = true;
  }

  delete(): void {
    if (this.confirmed == "yes") {
      this.reviewService.deleteReview(this.review.id).subscribe(result => {
        this.snackBar.open("Review Deleted Successfully", "close")
        window.location.reload();
      },
        error => {
          this.snackBar.open("Error: Please try again", "close")
        })
    } else {

    }
  }

  confirm(): void {
    const dialogRef = this.dialog.open(ConfirmComponent, {
    });
    dialogRef.afterClosed().subscribe(result => {
      this.confirmed = result;
      this.delete();
    });
  }
}
