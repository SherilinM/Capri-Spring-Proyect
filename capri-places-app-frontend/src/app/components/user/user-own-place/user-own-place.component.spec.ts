import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserOwnPlaceComponent } from './user-own-place.component';

describe('UserOwnPlaceComponent', () => {
  let component: UserOwnPlaceComponent;
  let fixture: ComponentFixture<UserOwnPlaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserOwnPlaceComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserOwnPlaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
