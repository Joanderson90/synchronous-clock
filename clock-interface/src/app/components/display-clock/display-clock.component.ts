import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ClockColorMap, ClockDTO } from 'src/app/models/clock/clock-dto';

@Component({
  selector: 'app-display-clock',
  templateUrl: './display-clock.component.html',
  styleUrls: ['./display-clock.component.scss'],
})
export class DisplayClockComponent {
  @Input()
  clock!: ClockDTO;
  @Output() newItemEvent = new EventEmitter<string>();

  getBorderColor(): string {
    return `border-color: ${ClockColorMap.get(this.clock?.clockName)}`;
  }
}
