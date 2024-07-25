export interface ClockDTO {
  clockName: string;
  isMaster: boolean;
  value: number;
  drift: number;
}

export enum ClockType {
  BIG_BEN = 'BIG_BEN',
  SHEPHERD_GATE_CLOCK = 'SHEPHERD_GATE_CLOCK',
  FLORAL_CLOCK = 'FLORAL_CLOCK',
}

export const ClockUrlMap = new Map<string, string>([
  ['BIG_BEN', 'http://172.16.103.14:8080/api/clock'],
  ['SHEPHERD_GATE_CLOCK', 'http://172.16.103.13:8081/api/clock'],
  ['FLORAL_CLOCK', 'http://172.16.103.12:8082/api/clock'],
]);

export const ClockColorMap = new Map<string, string>([
  ['Big Ben', '#b7effb'],
  ['Shepherd Gate Clock', '#c7f2cc'],
  ['Floral Clock', '#e3c6f0'],
]);
