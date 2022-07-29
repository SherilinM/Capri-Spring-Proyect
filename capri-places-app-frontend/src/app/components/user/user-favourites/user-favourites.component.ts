import { FavouritesService } from './../../../services/favourites.service';
import { Component, Input, OnInit } from '@angular/core';
import { PlaceDTO } from 'src/app/models/place.model';


@Component({
  selector: 'app-user-favourites',
  templateUrl: './user-favourites.component.html',
  styleUrls: ['./user-favourites.component.css']
})
export class UserFavouritesComponent implements OnInit {

  @Input()
  userId!: number;

  placeList!: PlaceDTO[];
  loading: boolean = true;

  panelOpenState: boolean = false;

  constructor(private favouritesService: FavouritesService) { }

  ngOnInit(): void {
    this.getFavouritePlaces();
  }

  getFavouritePlaces(): void {
    this.favouritesService.getFavouritePlacesByUserId(this.userId).subscribe(result => {
      this.placeList = result;
      this.loading = false;
      console.log(this.placeList)
    },
      error => {
        console.log("User currently has no favourites");
        this.loading = false;
      })
  }

}
