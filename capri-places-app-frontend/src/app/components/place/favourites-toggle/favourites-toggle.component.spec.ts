import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouritesToggleComponent } from './favourites-toggle.component';

describe('FavouritesToggleComponent', () => {
  let component: FavouritesToggleComponent;
  let fixture: ComponentFixture<FavouritesToggleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavouritesToggleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouritesToggleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
