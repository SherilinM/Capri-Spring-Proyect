import { PlaceDTO } from '../../../models/place.model';
import { PlaceService } from '../../../services/place.service';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-own-place',
  templateUrl: './user-own-place.component.html',
  styleUrls: ['./user-own-place.component.css']
})
export class UserOwnPlaceComponent implements OnInit {

  @Input()
  userId!: number;
  loading: boolean = true;

  placeList!: PlaceDTO[];

  panelOpenState: boolean = false;

  constructor(private placeService: PlaceService) {
  }

  ngOnInit(): void {
    this.getUsersOwnPlaces();
  }


  async getUsersOwnPlaces(): Promise<void> {
    try {
      this.placeList = await this.placeService.getPlacesByUserId(this.userId)
      this.loading = false;
    } catch (error) {
      this.loading = false;
      this.placeList = [];
    }
  }
}
