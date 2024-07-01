import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IExchangeRate } from '../exchange-rate.model';

@Component({
  standalone: true,
  selector: 'jhi-exchange-rate-detail',
  templateUrl: './exchange-rate-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ExchangeRateDetailComponent {
  exchangeRate = input<IExchangeRate | null>(null);

  previousState(): void {
    window.history.back();
  }
}
