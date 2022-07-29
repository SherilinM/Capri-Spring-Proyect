import { CustomValidators } from 'src/app/validators/custom-validators';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import { OktaAuth } from '@okta/okta-auth-js';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormArray, FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { PlaceService } from '../../../services/place.service';
import { EnumService } from '../../../services/enum.service';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { Place, PlaceDTO } from 'src/app/models/place.model';
import { UserDTO } from 'src/app/models/user-model';
import { Router } from '@angular/router';
import { Location } from '@angular/common'


@Component({
  selector: 'app-add-place-form',
  templateUrl: './add-place-form.component.html',
  styleUrls: ['./add-place-form.component.css']
})
export class AddPlaceFormComponent implements OnInit {

  user!: UserDTO;
  title: string = "Add a new place";
  authorsId!: number;
  categoryList!: string[];
  description!: string;
  addForm: boolean = true;
  addFormButton: string = "Add";
  formDisabled: boolean = false;
  loading: boolean = true;

  @Input()
  placeDTO!: PlaceDTO;

  place!: Place;

  placeForm!: FormGroup;
  name!: FormControl;
  authorId!: FormControl;
  category!: FormControl;
  theDescription!: FormControl;
  address!: FormControl;
  imageUrl!: FormControl;

  stepCount: number = 1;



  constructor(private enumService: EnumService,
    private placeService: PlaceService,
    private formBuilder: FormBuilder,
    private oktaAuth: OktaAuth,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: PlaceDTO,
    private snackBar: MatSnackBar,
    private router: Router,
    private location: Location
  ) {
    this.name = new FormControl('', [Validators.required, CustomValidators.nameValidator]);
    this.category = new FormControl('', [Validators.required]);
    this.address = new FormControl('', [Validators.required]);
    this.theDescription = new FormControl('', [Validators.required]);
    this.imageUrl = new FormControl('', [Validators.required, CustomValidators.urlValidator]);


    this.placeForm = new FormGroup({
      name: this.name,
      category: this.category,
      address: this.address,
      description: this.theDescription,
      imageUrl: this.imageUrl
    })
  }

  async ngOnInit(): Promise<void> {
    this.loadData();
    await this.getUserByEmail();
  }

  ngAfterViewInit(): void {
    this.addOrEdit();
  }

  private loadData() {
    this.getCategory();
    this.placeDTO = this.data;
  }

  onSubmit() {
    if (this.addForm) {
      this.addNewPlaceToDatabase();
    } else {
      this.editPlaceInDatabase();
    }
  }

  // Method to disable form after submission

  disableForm(): void {
    this.formDisabled = true;
    this.placeForm.disable();
  }


  // Logic for when form is loaded to display either Edit or Add information

  addOrEdit(): void {
    if (this.placeDTO.id != null) {
      this.addForm = false;
      this.addFormButton = "Confirm Edited"
      this.title = "Edit your place"
      this.fillFormToEdit();
    } else {
      this.addForm = true;
      this.addFormButton = "Add"
      this.title = "Enter your new place details...";
    }
    console.log(this.addForm)
  }

  fillFormToEdit(): void {
    this.placeForm.patchValue(this.placeDTO);
  }


  // Methods To load data for category list


  private getCategory(): void {
    this.enumService.getAllCategories().subscribe(categories => {
      this.categoryList = categories;
    })
  }

  // Methods to sent Post / Put requests to back end to either Add new Place or Edit existing

  private async addNewPlaceToDatabase() {
    const newPlace: PlaceDTO = this.createPlaceDTOFromForm();
    this.placeService.addPlace(newPlace).subscribe(result => {
      console.log(result)
      this.disableForm();
      this.snackBar.open("Success: Place Added", "close")
    },
      error => {
        this.snackBar.open("Failed to update details, please try again", "close")
      });
  }

  private editPlaceInDatabase() {
    this.authorsId = this.placeDTO.authorId;
    const editedPlace: PlaceDTO = this.createPlaceDTOFromForm();
    this.placeService.editPlace(editedPlace).subscribe(result => {
      console.log(result)
      this.disableForm();
      this.snackBar.open("Success: Place Edited", "close")
    },
      error => {
        this.snackBar.open("Failed to update details, please try again", "close")
      });
  }

  private createPlaceDTOFromForm() {
    let id: any;
    if (this.placeDTO == null) {
      id = 0;
    } else {
      id = this.placeDTO.id
    }
    return {
      id: id,
      name: this.name.value,
      authorId: this.authorsId,
      category: this.category.value,
      location: this.address.value,
      description: this.description,
      imageUrl: this.imageUrl.value
    };
  }

  // Gets logged in users details

  async getLoggedInEmail(): Promise<string> {
    let loggedInEmail: string = String((await this.oktaAuth.getUser()).preferred_username);
    return loggedInEmail;
  }

  async getUserByEmail(): Promise<void> {
    try {
      this.user = await this.userService.getUserByEmail(await this.getLoggedInEmail())
      this.authorsId = this.user.id;
      this.loading = false;
    } catch (error) {
      this.snackBar.open("Please ensure your details are correct before creating a place", "close")
      this.router.navigateByUrl("/profile")
    }
  }

  back(): void {
    this.location.back();
  }



}
