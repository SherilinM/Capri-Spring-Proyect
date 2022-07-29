import { EnumPipe } from '../../../custom-pipes/enum-pipe';
import { PlaceInstructionsComponent } from './../place-page/place-instructions/place-instructions.component';
import { AddPlaceFormComponent } from '../add-place-form/add-place-form.component';
import { PlaceSearchComponent } from '../place-search/place-search.component';
import { PlaceService } from '../../../services/place.service';
import { PlaceDTO } from 'src/app/models/place.model';
import { EnumService } from '../../../services/enum.service';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { OktaAuthStateService } from '@okta/okta-angular';

@Component({
  selector: 'app-place-browser',
  templateUrl: './place-browser.component.html',
  styleUrls: ['./place-browser.component.css']
})
export class PlaceBrowserComponent implements OnInit {

  categoryList!: string[];
  searchValue!: string;
  placeFormTitle: string = "Add a new place"
  loading: boolean = true;
  enumPipe: EnumPipe = new EnumPipe();

  @Input()
  showList: boolean = true
  @Input()
  showAddButton: boolean = true

  placeList!: PlaceDTO[];
  filterList: string[];
  filterActive: boolean = false;
  filteredPlaceList!: PlaceDTO[];

  @ViewChild(PlaceSearchComponent) placeSearchComponent!: PlaceSearchComponent;

  constructor(private enumService: EnumService,
    private placeService: PlaceService,
    private router: Router,
    private dialog: MatDialog,
    public authService: OktaAuthStateService) {
    this.filterList = [],
      this.placeList = []
  }

  async ngOnInit(): Promise<void> {
    await this.loadData();
  }

  // Get enums for the chips and form

  async loadData(): Promise<void> {
    this.getAllCategories();
    await this.getAllPlaces();
    this.loading = false;
  }

  getAllCategories(): void {
    this.enumService.getAllCategories().subscribe(result => {
      this.categoryList = result
    })
  }


  // Populates the place lists
  async getAllPlaces(): Promise<void> {
    this.placeList = await this.placeService.getAllPlaces()
    this.loading = false;
    this.populateFilteredList();
  }

  public populateFilteredList(): void {
    this.filteredPlaceList = this.placeList;
  }

  // Chip handling

  toggleSelection(chip: MatChip) {
    chip.toggleSelected();
    this.alterFilterList(chip);
    this.toggleFilterActive();
  }

  private alterFilterList(chip: MatChip) {
    if (chip.selected) {
      this.filterList.push(chip.value);
      this.filterPlaceList();
    } else {
      let index: number = this.filterList.indexOf(chip.value);
      this.filterList.splice(index, 1);
      this.filterPlaceList();
    }
  }

  private toggleFilterActive(): void {
    if (this.filterList.length > 0) {
      this.filterActive = true;
    } else {
      this.filterActive = false;
    }
  }

  // Filter using chips & search bar is not null 

  private filterPlaceList(): void {
    if (this.filterList.length == 0) {
      this.filteredPlaceList = this.placeList
    } else {
      this.filteredPlaceList = this.placeList.filter((place => this.filterList.includes(place.category)));
    }
    if (this.searchValue != null) {
      this.filterByText(this.searchValue)
    }
  }

  // Filter using the search bar 
  filterByText(placeName: any) {
    this.searchValue = placeName;
    const filterValue: string = placeName
    this.filteredPlaceList = this.filteredPlaceList
      .filter(place => place.name.toLowerCase().includes(filterValue) || this.enumPipe.transform(place.category).toLowerCase().includes(filterValue));
  }

  // When user deletes text in the search bar this updates to ensure search is correct
  updateSearchValue(placeName: any) {
    this.searchValue = placeName;
    this.filterPlaceList()
    if (this.filterList.length == 0 && this.searchValue == "") {
      this.filteredPlaceList = this.placeList;
    }
  }

  // Reloads page when all is clicked 
  reloadPage(): void {
    if (this.filterActive || this.searchValue != null) {
      window.location.reload();
    }
  }

  // Loads the add place form! 
  loadAddForm(): void {
    this.router.navigateByUrl("/addplace")
  }

}
