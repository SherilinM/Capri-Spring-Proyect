import { PlaceDTO } from '../../../../models/place.model';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-place-instructions',
  templateUrl: './place-instructions.component.html',
  styleUrls: ['./place-instructions.component.css']
})
export class PlaceInstructionsComponent implements OnInit {

  @Input()
  place!: PlaceDTO;

  constructor() { }

  ngOnInit(): void {
  }

}
