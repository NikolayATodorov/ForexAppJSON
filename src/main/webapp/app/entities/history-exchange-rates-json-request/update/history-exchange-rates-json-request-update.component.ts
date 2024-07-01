import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';
import { HistoryExchangeRatesJsonRequestService } from '../service/history-exchange-rates-json-request.service';
import {
  HistoryExchangeRatesJsonRequestFormService,
  HistoryExchangeRatesJsonRequestFormGroup,
} from './history-exchange-rates-json-request-form.service';

@Component({
  standalone: true,
  selector: 'jhi-history-exchange-rates-json-request-update',
  templateUrl: './history-exchange-rates-json-request-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HistoryExchangeRatesJsonRequestUpdateComponent implements OnInit {
  isSaving = false;
  historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest | null = null;

  protected historyExchangeRatesJsonRequestService = inject(HistoryExchangeRatesJsonRequestService);
  protected historyExchangeRatesJsonRequestFormService = inject(HistoryExchangeRatesJsonRequestFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HistoryExchangeRatesJsonRequestFormGroup =
    this.historyExchangeRatesJsonRequestFormService.createHistoryExchangeRatesJsonRequestFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historyExchangeRatesJsonRequest }) => {
      this.historyExchangeRatesJsonRequest = historyExchangeRatesJsonRequest;
      if (historyExchangeRatesJsonRequest) {
        this.updateForm(historyExchangeRatesJsonRequest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historyExchangeRatesJsonRequest = this.historyExchangeRatesJsonRequestFormService.getHistoryExchangeRatesJsonRequest(
      this.editForm,
    );
    if (historyExchangeRatesJsonRequest.id !== null) {
      this.subscribeToSaveResponse(this.historyExchangeRatesJsonRequestService.update(historyExchangeRatesJsonRequest));
    } else {
      this.subscribeToSaveResponse(this.historyExchangeRatesJsonRequestService.create(historyExchangeRatesJsonRequest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistoryExchangeRatesJsonRequest>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest): void {
    this.historyExchangeRatesJsonRequest = historyExchangeRatesJsonRequest;
    this.historyExchangeRatesJsonRequestFormService.resetForm(this.editForm, historyExchangeRatesJsonRequest);
  }
}
