import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { repeat } from 'rxjs';
import {
  ClockDTO,
  ClockType,
  ClockUrlMap,
} from 'src/app/models/clock/clock-dto';
import { ClockServiceService } from '../base/clock-service.service';

@Component({
  selector: 'app-clock-control',
  templateUrl: './clock-control.component.html',
  styleUrls: ['./clock-control.component.scss'],
})
export class ClockControlComponent {
  clockForm!: FormGroup;
  bigBen!: ClockDTO;
  shepherdGate!: ClockDTO;
  floral!: ClockDTO;

  MEIO_SEGUNDO_EM_MILISSEGUNDO: number = 1e3 / 2;

  constructor(
    private _clockService: ClockServiceService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    ClockUrlMap.forEach((value: string, key: string) => {
      this._clockService
        .getTime(value)
        .pipe(repeat({ delay: this.MEIO_SEGUNDO_EM_MILISSEGUNDO }))
        .subscribe({
          next: (clockInfo) => {
            if (key === ClockType.BIG_BEN) this.bigBen = clockInfo;
            else if (key === ClockType.SHEPHERD_GATE_CLOCK)
              this.shepherdGate = clockInfo;
            else this.floral = clockInfo;
          },
          error: () => console.log('Error to get time.'),
        });
    });
  }
}
