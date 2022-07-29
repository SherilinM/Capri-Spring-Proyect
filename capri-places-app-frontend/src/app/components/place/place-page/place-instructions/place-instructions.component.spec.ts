import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceInstructionsComponent } from './place-instructions.component';

describe('PlaceInstructionsComponent', () => {
  let component: PlaceInstructionsComponent;
  let fixture: ComponentFixture<PlaceInstructionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlaceInstructionsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaceInstructionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
