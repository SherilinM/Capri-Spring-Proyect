import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceAccordionComponent } from './place-accordion.component';

describe('PlaceAccordionComponent', () => {
  let component: PlaceAccordionComponent;
  let fixture: ComponentFixture<PlaceAccordionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlaceAccordionComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaceAccordionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
