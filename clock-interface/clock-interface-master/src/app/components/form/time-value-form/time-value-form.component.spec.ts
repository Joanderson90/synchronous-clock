import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeValueFormComponent } from './time-value-form.component';

describe('TimeValueFormComponent', () => {
  let component: TimeValueFormComponent;
  let fixture: ComponentFixture<TimeValueFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimeValueFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TimeValueFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
