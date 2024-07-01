import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';
import { CurrentExchangeRatesJsonRequestService } from '../service/current-exchange-rates-json-request.service';
import {
  CurrentExchangeRatesJsonRequestFormService,
  CurrentExchangeRatesJsonRequestFormGroup,
} from './current-exchange-rates-json-request-form.service';

@Component({
  standalone: true,
  selector: 'jhi-current-exchange-rates-json-request-update',
  templateUrl: './current-exchange-rates-json-request-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CurrentExchangeRatesJsonRequestUpdateComponent implements OnInit {
  isSaving = false;
  currentExchangeRatesJsonRequest: ICurrentExchangeRatesJsonRequest | null = null;

  protected currentExchangeRatesJsonRequestService = inject(CurrentExchangeRatesJsonRequestService);
  protected currentExchangeRatesJsonRequestFormService = inject(CurrentExchangeRatesJsonRequestFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CurrentExchangeRatesJsonRequestFormGroup =
    this.currentExchangeRatesJsonRequestFormService.createCurrentExchangeRatesJsonRequestFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currentExchangeRatesJsonRequest }) => {
      this.currentExchangeRatesJsonRequest = currentExchangeRatesJsonRequest;
      if (currentExchangeRatesJsonRequest) {
        this.updateForm(currentExchangeRatesJsonRequest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currentExchangeRatesJsonRequest = this.currentExchangeRatesJsonRequestFormService.getCurrentExchangeRatesJsonRequest(
      this.editForm,
    );
    if (currentExchangeRatesJsonRequest.id !== null) {
      this.subscribeToSaveResponse(this.currentExchangeRatesJsonRequestService.update(currentExchangeRatesJsonRequest));
    } else {
      this.subscribeToSaveResponse(this.currentExchangeRatesJsonRequestService.create(currentExchangeRatesJsonRequest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrentExchangeRatesJsonRequest>>): void {
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

  protected updateForm(currentExchangeRatesJsonRequest: ICurrentExchangeRatesJsonRequest): void {
    this.currentExchangeRatesJsonRequest = currentExchangeRatesJsonRequest;
    this.currentExchangeRatesJsonRequestFormService.resetForm(this.editForm, currentExchangeRatesJsonRequest);
  }
}
