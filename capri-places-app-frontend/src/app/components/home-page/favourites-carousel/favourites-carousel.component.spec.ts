import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouritesCarouselComponent } from './favourites-carousel.component';

describe('FavouritesCarouselComponent', () => {
  let component: FavouritesCarouselComponent;
  let fixture: ComponentFixture<FavouritesCarouselComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavouritesCarouselComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouritesCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
