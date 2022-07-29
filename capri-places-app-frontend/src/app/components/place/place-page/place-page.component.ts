import { MatSnackBar } from '@angular/material/snack-bar';
import { OktaAuth } from '@okta/okta-auth-js';
import { MatBadgeModule } from '@angular/material/badge';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from './../../../services/user.service';
import { UserDTO } from './../../../models/user-model';
import { PlaceService } from '../../../services/place.service';
import { PlaceDTO } from '../../../models/place.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AddPlaceFormComponent } from '../add-place-form/add-place-form.component';
import { Location } from '@angular/common'
import { ConfirmComponent } from '../../dialogs/confirm/confirm.component';

@Component({
  selector: 'app-place-page',
  templateUrl: './place-page.component.html',
  styleUrls: ['./place-page.component.css']
})
export class PlacePageComponent implements OnInit {

  placeDTO!: PlaceDTO;
  user!: UserDTO;
  loadingPlace: boolean = true;

  loggedInEmail!: string;

  userVerified: boolean = false;
  username!: string;
  confirmed: string = "no";

  constructor(private placeService: PlaceService,
    private activateRoute: ActivatedRoute,
    private userService: UserService,
    private dialog: MatDialog,
    private location: Location,
    private oktaAuth: OktaAuth,
    private router: Router,
    private snackBar: MatSnackBar) { }

  async ngOnInit(): Promise<void> {
    const placeId: number = this.activateRoute.snapshot.params['placeId'];
    await this.getPlace(placeId);
  }

  async getPlace(id: number): Promise<void> {
    try {
      this.placeDTO = await this.placeService.getPlaceById(id)
    } catch (error) {
      this.snackBar.open("Page Not Found - You Have Been Redirected", "close")
      this.router.navigate(['places']);
    }
    this.user = await this.userService.getUserById(this.placeDTO.authorId);
    await this.verifyUser();
    this.loadingPlace = false;
  }

  loadAddForm(): void {
    const dialogRef = this.dialog.open(AddPlaceFormComponent, { autoFocus: false, height: '80vh', width: '80vw', data: this.placeDTO });
    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog result: %{result}');
      window.location.reload();
    })
  }

  back(): void {
    this.location.back()
  }

  refreshPage(): void {
    window.location.reload();
  }

  async verifyUser() {
    this.userVerified = await this.emailsMatch();
  }

  async emailsMatch(): Promise<boolean> {
    try {
      this.loggedInEmail = String((await this.oktaAuth.getUser()).preferred_username);
      if (this.user.email == this.loggedInEmail) {
        return true;
      } else {
        return false;
      }
    } catch (error) {
      return false;
    }
  }

  delete(): void {
    if (this.confirmed == "yes") {
      this.placeService.deletePlace(this.placeDTO.id).subscribe(result => {
        console.log(result)
        this.snackBar.open("Deleted Successfully", "CLOSE")
      },
        error => {
          this.snackBar.open("An error occured: Please try again", "CLOSE")
        })
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
