import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';
import { CurrentExchangeRatesJsonRequestService } from '../service/current-exchange-rates-json-request.service';

@Component({
  standalone: true,
  templateUrl: './current-exchange-rates-json-request-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CurrentExchangeRatesJsonRequestDeleteDialogComponent {
  currentExchangeRatesJsonRequest?: ICurrentExchangeRatesJsonRequest;

  protected currentExchangeRatesJsonRequestService = inject(CurrentExchangeRatesJsonRequestService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.currentExchangeRatesJsonRequestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
