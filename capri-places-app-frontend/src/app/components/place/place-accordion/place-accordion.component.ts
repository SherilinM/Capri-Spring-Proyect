import { Component, Input, OnInit } from '@angular/core';
import { PlaceDTO } from 'src/app/models/place.model';

@Component({
  selector: 'app-place-accordion',
  templateUrl: './place-accordion.component.html',
  styleUrls: ['./place-accordion.component.css']
})
export class PlaceAccordionComponent implements OnInit {

  @Input()
  placeList!: PlaceDTO[];
  loading: boolean = true;

  panelOpenState: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
