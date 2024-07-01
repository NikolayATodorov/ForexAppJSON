import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';

@Component({
  standalone: true,
  selector: 'jhi-history-exchange-rates-json-request-detail',
  templateUrl: './history-exchange-rates-json-request-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class HistoryExchangeRatesJsonRequestDetailComponent {
  historyExchangeRatesJsonRequest = input<IHistoryExchangeRatesJsonRequest | null>(null);

  previousState(): void {
    window.history.back();
  }
}
