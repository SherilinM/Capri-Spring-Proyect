import { OktaAuth } from '@okta/okta-auth-js';
import { UserService } from 'src/app/services/user.service';
import { Component, OnInit } from '@angular/core';
import { UserDTO } from 'src/app/models/user-model';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  loading: boolean = true;


  user!: UserDTO;

  constructor(private userService:UserService, private oktaAuth:OktaAuth) { }

  ngOnInit(): void {
    this.loadProfile();
  }


async loadProfile():Promise<void>{
  this.userService.loadProfilePage().subscribe(async result => {
    this.user = result;
    await this.fillDetailsIfEmpty();
    this.loading = false;
  })
}

async fillDetailsIfEmpty():Promise<void> {
  await this.fillNameIfEmpty();
  await this.fillUsernameIfEmpty();
  await this.updatUserDetails();
}


  private async fillUsernameIfEmpty() {
    if (this.user.username == "" || this.user.username == null) {
      const rndInt = Math.floor(Math.random() * 3278235) + 1;
      console.log(rndInt);
      let username = String((await this.oktaAuth.getUser()).name);
      this.user.username = username.replace(/\s+/g, '').concat(String(rndInt));
    }
  }

  private async fillNameIfEmpty() {
    if (this.user.name == "" || this.user.name == null) {
      let name = String((await this.oktaAuth.getUser()).name);
      console.log(name)
      this.user.name = name;
    }
  }

  private async updatUserDetails() {
    this.userService.editUser(this.user).subscribe(result => {
      this.user = result;
    })
  }
}
