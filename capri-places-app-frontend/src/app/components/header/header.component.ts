import { UserService } from 'src/app/services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { Route } from '@angular/compiler/src/core';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { UserFormComponent } from '../user/user-form/user-form.component';
import { OktaAuth } from '@okta/okta-auth-js';
import { OktaAuthStateService } from '@okta/okta-angular';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {


  @Output() toggleMenu: EventEmitter<string> = new EventEmitter();

  constructor(private router: Router,
    private dialog: MatDialog,
    public oktaAuth: OktaAuth, public authService: OktaAuthStateService,
    private userService: UserService) { }


  ngOnInit(): void {
  }

  loadPlaces(): void {
    console.log("working")
    this.router.navigateByUrl("/places/")
  }

  menuClicked(): void {
    this.toggleMenu.emit("clicked");
  }


}
