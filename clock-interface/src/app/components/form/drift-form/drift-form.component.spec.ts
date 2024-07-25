import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriftFormComponent } from './drift-form.component';

describe('DriftFormComponent', () => {
  let component: DriftFormComponent;
  let fixture: ComponentFixture<DriftFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DriftFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DriftFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
