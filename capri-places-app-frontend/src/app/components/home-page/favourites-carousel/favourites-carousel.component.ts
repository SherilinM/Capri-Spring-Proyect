import { UserDTO } from './../../../models/user-model';
import { UserService } from 'src/app/services/user.service';
import { PlaceDTO } from 'src/app/models/place.model';
import { FavouritesService } from './../../../services/favourites.service';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgbCarousel, NgbSlideEvent, NgbSlideEventSource } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-favourites-carousel',
  templateUrl: './favourites-carousel.component.html',
  styleUrls: ['./favourites-carousel.component.css']
})
export class FavouritesCarouselComponent implements OnInit {

  loading: boolean = true;

  constructor(private favouritesService: FavouritesService,
    private userService: UserService) { }

  @Input()
  placeList!: PlaceDTO[];

  page: number = 0;

  async ngOnInit(): Promise<void> {
    await this.getTop10Favourites();
  }

  async getTop10Favourites(): Promise<void> {
    this.placeList = await this.favouritesService.getTop10FavouritedPlaces();
    this.loading = false;
  }


} 
