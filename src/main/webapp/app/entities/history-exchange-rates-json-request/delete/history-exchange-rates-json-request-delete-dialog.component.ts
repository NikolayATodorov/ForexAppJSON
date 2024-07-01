import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';
import { HistoryExchangeRatesJsonRequestService } from '../service/history-exchange-rates-json-request.service';

@Component({
  standalone: true,
  templateUrl: './history-exchange-rates-json-request-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HistoryExchangeRatesJsonRequestDeleteDialogComponent {
  historyExchangeRatesJsonRequest?: IHistoryExchangeRatesJsonRequest;

  protected historyExchangeRatesJsonRequestService = inject(HistoryExchangeRatesJsonRequestService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historyExchangeRatesJsonRequestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
