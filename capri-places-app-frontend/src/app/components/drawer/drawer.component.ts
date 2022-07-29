import { Router } from '@angular/router';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';

@Component({
  selector: 'app-drawer',
  templateUrl: './drawer.component.html',
  styleUrls: ['./drawer.component.css']
})
export class DrawerComponent implements OnInit {

  @Output() closeDrawer: EventEmitter<string> = new EventEmitter();


  constructor(private router: Router,
    public authService: OktaAuthStateService) { }

  ngOnInit(): void {
  }

  loadPlacePage(): void {
    this.router.navigate(['places']);
    this.closeSideMenu();
  }

  closeSideMenu(): void {
    this.closeDrawer.emit("Close");
  }

  loadProfile(): void {
    this.router.navigate(['profile']);
    this.closeSideMenu();
  }

  loadAddPlace(): void {
    this.router.navigate(['/addplace']);
    this.closeSideMenu();
  }



}
