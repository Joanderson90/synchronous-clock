import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ClockControlComponent } from './components/clock-control/clock-control.component';
import { DisplayClockComponent } from './components/display-clock/display-clock.component';
import { TimeValueFormComponent } from './components/form/time-value-form/time-value-form.component';
import { DriftFormComponent } from './components/form/drift-form/drift-form.component';

@NgModule({
  declarations: [AppComponent, ClockControlComponent, DisplayClockComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CommonModule,
    HttpClientModule,
    TimeValueFormComponent,
    DriftFormComponent,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
