import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClockDTO } from 'src/app/models/clock/clock-dto';
import { TimeDTO } from 'src/app/models/time/time-dto';

@Injectable({
  providedIn: 'root',
})
export class ClockServiceService {
  private apiUrl!: string;

  protected constructor(protected http: HttpClient) {}

  getTime(clockUrl: string): Observable<ClockDTO> {
    return this.http.get<ClockDTO>(clockUrl + '/clockInfo');
  }

  updateTime(
    clockUrl: string | undefined,
    timeValue: number
  ): Observable<TimeDTO> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('value', timeValue);

    const options = { headers: this.getHeaders(), params: httpParams };

    return this.http.patch<TimeDTO>(
      clockUrl + '/updateTimeValue',
      timeValue,
      options
    );
  }

  updateDrift(
    clockUrl: string | undefined,
    drift: number
  ): Observable<TimeDTO> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('drift', drift);

    const options = { headers: this.getHeaders(), params: httpParams };

    return this.http.patch<TimeDTO>(clockUrl + '/updateDrift', drift, options);
  }

  public getHeaders(): HttpHeaders {
    let customHeader = new HttpHeaders().set(
      'Content-Type',
      'application/json'
    );

    return customHeader;
  }
}
