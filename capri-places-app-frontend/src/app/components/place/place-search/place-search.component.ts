import { EnumPipe } from '../../../custom-pipes/enum-pipe';
import { PlaceService } from '../../../services/place.service';
import { PlaceDTO } from 'src/app/models/place.model';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-place-search',
  templateUrl: './place-search.component.html',
  styleUrls: ['./place-search.component.css']
})
export class PlaceSearchComponent implements OnInit {

  @Input()
  placeList!: PlaceDTO[];
  @Input()
  external: boolean = false;

  loading: boolean = true;

  filteredPlaceList!: Observable<PlaceDTO[]>;

  placeSearch: FormControl;
  placeForm: FormGroup;

  enumPipe: EnumPipe = new EnumPipe();

  placeId!: number;

  @Output() placeNameOutput: EventEmitter<string> = new EventEmitter();
  @Output() clickedPlaceNameOutput: EventEmitter<string> = new EventEmitter();
  @Output() deleteOutput: EventEmitter<string> = new EventEmitter();
  @Output() placeIdOutput: EventEmitter<string> = new EventEmitter();

  constructor(private placeService: PlaceService) {
    this.placeSearch = new FormControl('');
    this.placeForm = new FormGroup({
      placeSearch: this.placeSearch
    })
  }

  async ngOnInit(): Promise<void> {
    await this.getAllPlaces();
    this.filteredPlaceList = this.placeForm.valueChanges.pipe(
      startWith(''),
      map(value => this._filter()),
    )
    this.loading = false;
  }


  async getAllPlaces(): Promise<void> {
    if (this.placeList == null)
      this.placeList = await this.placeService.getAllPlaces();
    this.filteredPlaceList = this.placeForm.valueChanges.pipe(
      startWith(''),
      map(value => this._filter())
    )
  }

  private _filter(): PlaceDTO[] {
    const filterValue: string = this.placeSearch.value.toLowerCase().trim();
    return this.placeList
      .filter(place => place.name.toLowerCase().includes(filterValue) || this.enumPipe.transform(place.category).toLowerCase().includes(filterValue));
  }

  sendPlaceName(): void {
    this.placeNameOutput.emit(this.placeSearch.value.toLowerCase().trim());
  }

  sendClickedPlaceName(): void {
    this.clickedPlaceNameOutput.emit(this.placeSearch.value.toLowerCase().trim());
  }

  delete(): void {
    this.deleteOutput.emit(this.placeSearch.value.toLowerCase().trim());
  }

  sendPlaceId(): void {
    try {
      this.placeIdOutput.emit(this.placeSearch.value.id)
    } catch (error) {
      window.location.reload();
    }
  }

  displayFn(place: PlaceDTO): string {
    return place && place.name ? place.name : '';
  }

}
