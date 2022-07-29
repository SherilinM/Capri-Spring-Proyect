import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaceBrowserComponent } from './place-browser.component';

describe('PlaceBrowserComponent', () => {
  let component: PlaceBrowserComponent;
  let fixture: ComponentFixture<PlaceBrowserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlaceBrowserComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaceBrowserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
