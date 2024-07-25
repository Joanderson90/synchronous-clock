import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClockControlComponent } from './clock-control.component';

describe('ClockControlComponent', () => {
  let component: ClockControlComponent;
  let fixture: ComponentFixture<ClockControlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClockControlComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClockControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
