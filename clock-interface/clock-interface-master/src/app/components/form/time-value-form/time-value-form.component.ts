import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClockUrlMap } from 'src/app/models/clock/clock-dto';
import { ClockServiceService } from '../../base/clock-service.service';

@Component({
  selector: 'app-time-value-form',
  templateUrl: './time-value-form.component.html',
  styleUrls: ['./time-value-form.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
})
export class TimeValueFormComponent {
  @Input()
  clockName!: string;

  timeForm: FormControl;

  constructor(private _clockService: ClockServiceService) {
    this.timeForm = new FormControl(1, Validators.min(1));
  }

  changeTime() {
    this._clockService
      .updateTime(ClockUrlMap.get(this.clockName), this.timeForm.value)

      .subscribe({
        next: (time) => {
          console.log('Success to update time.');
          console.log(time);
        },
        error: () => console.log('Error to update time.'),
      });
  }
}
