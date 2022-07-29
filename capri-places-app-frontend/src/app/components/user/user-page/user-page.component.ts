import { UserFormComponent } from './../user-form/user-form.component';
import { MatDialog } from '@angular/material/dialog';
import { UserOwnPlaceComponent } from './../user-own-place/user-own-place.component';
import { ActivatedRoute } from '@angular/router';
import { UserDTO } from './../../../models/user-model';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Location } from '@angular/common'

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  @Input()
  user!: UserDTO;
  loading: boolean = true;

  constructor(private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) { }

  async ngOnInit(): Promise<void> {
    await this.getUserIfNoInputtedUser();
    this.loading = false;
  }

  async getUserIfNoInputtedUser(): Promise<void> {
    try {
      if (this.user == null) {
        const userId: number = this.activatedRoute.snapshot.params['userId'];
        await this.getUser(userId);
      }
    } catch (error) {

    }
  }

  async getUser(id: number): Promise<void> {
    this.user = await this.userService.getUserById(id)
  }


  back(): void {
    this.location.back();
  }



}
