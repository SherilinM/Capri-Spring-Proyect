import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatingCarouselGenericComponent } from './rating-carousel-generic.component';

describe('RatingCarouselGenericComponent', () => {
  let component: RatingCarouselGenericComponent;
  let fixture: ComponentFixture<RatingCarouselGenericComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RatingCarouselGenericComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingCarouselGenericComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
