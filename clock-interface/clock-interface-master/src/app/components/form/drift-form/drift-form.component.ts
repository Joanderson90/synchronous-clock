import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClockUrlMap } from 'src/app/models/clock/clock-dto';
import { ClockServiceService } from '../../base/clock-service.service';

@Component({
  selector: 'app-drift-form',
  templateUrl: './drift-form.component.html',
  styleUrls: ['./drift-form.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
})
export class DriftFormComponent {
  @Input()
  clockName!: string;

  driftForm: FormControl;

  constructor(private _clockService: ClockServiceService) {
    this.driftForm = new FormControl(1, Validators.min(0));
  }

  changeDrift() {
    this._clockService
      .updateDrift(ClockUrlMap.get(this.clockName), this.driftForm.value)

      .subscribe({
        next: (time) => {
          console.log('Success to update drift.');
          console.log(time);
        },
        error: () => console.log('Error to update drift.'),
      });
  }
}
