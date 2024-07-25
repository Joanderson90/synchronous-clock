import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayClockComponent } from './display-clock.component';

describe('DisplayClockComponent', () => {
  let component: DisplayClockComponent;
  let fixture: ComponentFixture<DisplayClockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplayClockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisplayClockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
