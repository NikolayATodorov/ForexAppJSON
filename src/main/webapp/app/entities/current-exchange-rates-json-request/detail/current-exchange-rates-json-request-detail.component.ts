import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';

@Component({
  standalone: true,
  selector: 'jhi-current-exchange-rates-json-request-detail',
  templateUrl: './current-exchange-rates-json-request-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CurrentExchangeRatesJsonRequestDetailComponent {
  currentExchangeRatesJsonRequest = input<ICurrentExchangeRatesJsonRequest | null>(null);

  previousState(): void {
    window.history.back();
  }
}
