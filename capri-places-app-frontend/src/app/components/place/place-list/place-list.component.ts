import { PlaceService } from '../../../services/place.service';
import { PlaceDTO } from '../../../models/place.model';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-place-list',
  templateUrl: './place-list.component.html',
  styleUrls: ['./place-list.component.css']
})
export class PlaceListComponent implements OnInit {

  @Input()
  placeList!: PlaceDTO[];

  @Input()
  filter!: string;

  page: number = 1;

  constructor() { }

  ngOnInit(): void {

  }

  handlePageChange(event: number): void {
    this.page = event;
  }

}
