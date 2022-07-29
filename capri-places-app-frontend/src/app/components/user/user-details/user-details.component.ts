import { OktaAuth } from '@okta/okta-auth-js';
import { MatDialog } from '@angular/material/dialog';
import { UserDTO } from './../../../models/user-model';
import { Component, Input, OnInit } from '@angular/core';
import { UserFormComponent } from '../user-form/user-form.component';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  @Input()
  user!:UserDTO;
  loading:boolean = true;

  loggedInEmail: string | undefined;

  userVerified: boolean = false;
  username!: string;

  constructor(private dialog: MatDialog, private oktaAuth:OktaAuth) { }

  async ngOnInit(): Promise<void> {
    await this.verifyUser();
    this.loading = false;

  }

  loadUserForm(): void {
    const dialogRef = this.dialog.open(UserFormComponent, { autoFocus: true, maxHeight: '85vh', width: '85vw', maxWidth: "600px", data: this.user});
    dialogRef.afterClosed().subscribe(result => {
      this.refreshPage();
    })
  }

  refreshPage():void {
    window.location.reload();
  }

  async verifyUser(){
    this.userVerified = (await this.emailsMatch());
  }

  async emailsMatch(): Promise<boolean> {
    this.loggedInEmail = (await this.oktaAuth.getUser()).preferred_username;
    if(this.user.email == this.loggedInEmail){
      return true;
    } else {
      return false;
    }
  }


}
