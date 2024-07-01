import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHistoryExchangeRatesJsonRequest, NewHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHistoryExchangeRatesJsonRequest for edit and NewHistoryExchangeRatesJsonRequestFormGroupInput for create.
 */
type HistoryExchangeRatesJsonRequestFormGroupInput =
  | IHistoryExchangeRatesJsonRequest
  | PartialWithRequiredKeyOf<NewHistoryExchangeRatesJsonRequest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IHistoryExchangeRatesJsonRequest | NewHistoryExchangeRatesJsonRequest> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type HistoryExchangeRatesJsonRequestFormRawValue = FormValueOf<IHistoryExchangeRatesJsonRequest>;

type NewHistoryExchangeRatesJsonRequestFormRawValue = FormValueOf<NewHistoryExchangeRatesJsonRequest>;

type HistoryExchangeRatesJsonRequestFormDefaults = Pick<NewHistoryExchangeRatesJsonRequest, 'id' | 'timestamp'>;

type HistoryExchangeRatesJsonRequestFormGroupContent = {
  id: FormControl<HistoryExchangeRatesJsonRequestFormRawValue['id'] | NewHistoryExchangeRatesJsonRequest['id']>;
  requestId: FormControl<HistoryExchangeRatesJsonRequestFormRawValue['requestId']>;
  timestamp: FormControl<HistoryExchangeRatesJsonRequestFormRawValue['timestamp']>;
  client: FormControl<HistoryExchangeRatesJsonRequestFormRawValue['client']>;
  currency: FormControl<HistoryExchangeRatesJsonRequestFormRawValue['currency']>;
  period: FormControl<HistoryExchangeRatesJsonRequestFormRawValue['period']>;
};

export type HistoryExchangeRatesJsonRequestFormGroup = FormGroup<HistoryExchangeRatesJsonRequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HistoryExchangeRatesJsonRequestFormService {
  createHistoryExchangeRatesJsonRequestFormGroup(
    historyExchangeRatesJsonRequest: HistoryExchangeRatesJsonRequestFormGroupInput = { id: null },
  ): HistoryExchangeRatesJsonRequestFormGroup {
    const historyExchangeRatesJsonRequestRawValue = this.convertHistoryExchangeRatesJsonRequestToHistoryExchangeRatesJsonRequestRawValue({
      ...this.getFormDefaults(),
      ...historyExchangeRatesJsonRequest,
    });
    return new FormGroup<HistoryExchangeRatesJsonRequestFormGroupContent>({
      id: new FormControl(
        { value: historyExchangeRatesJsonRequestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      requestId: new FormControl(historyExchangeRatesJsonRequestRawValue.requestId, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(historyExchangeRatesJsonRequestRawValue.timestamp, {
        validators: [Validators.required],
      }),
      client: new FormControl(historyExchangeRatesJsonRequestRawValue.client, {
        validators: [Validators.required],
      }),
      currency: new FormControl(historyExchangeRatesJsonRequestRawValue.currency, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(3)],
      }),
      period: new FormControl(historyExchangeRatesJsonRequestRawValue.period, {
        validators: [Validators.required],
      }),
    });
  }

  getHistoryExchangeRatesJsonRequest(
    form: HistoryExchangeRatesJsonRequestFormGroup,
  ): IHistoryExchangeRatesJsonRequest | NewHistoryExchangeRatesJsonRequest {
    return this.convertHistoryExchangeRatesJsonRequestRawValueToHistoryExchangeRatesJsonRequest(
      form.getRawValue() as HistoryExchangeRatesJsonRequestFormRawValue | NewHistoryExchangeRatesJsonRequestFormRawValue,
    );
  }

  resetForm(
    form: HistoryExchangeRatesJsonRequestFormGroup,
    historyExchangeRatesJsonRequest: HistoryExchangeRatesJsonRequestFormGroupInput,
  ): void {
    const historyExchangeRatesJsonRequestRawValue = this.convertHistoryExchangeRatesJsonRequestToHistoryExchangeRatesJsonRequestRawValue({
      ...this.getFormDefaults(),
      ...historyExchangeRatesJsonRequest,
    });
    form.reset(
      {
        ...historyExchangeRatesJsonRequestRawValue,
        id: { value: historyExchangeRatesJsonRequestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HistoryExchangeRatesJsonRequestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertHistoryExchangeRatesJsonRequestRawValueToHistoryExchangeRatesJsonRequest(
    rawHistoryExchangeRatesJsonRequest: HistoryExchangeRatesJsonRequestFormRawValue | NewHistoryExchangeRatesJsonRequestFormRawValue,
  ): IHistoryExchangeRatesJsonRequest | NewHistoryExchangeRatesJsonRequest {
    return {
      ...rawHistoryExchangeRatesJsonRequest,
      timestamp: dayjs(rawHistoryExchangeRatesJsonRequest.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertHistoryExchangeRatesJsonRequestToHistoryExchangeRatesJsonRequestRawValue(
    historyExchangeRatesJsonRequest:
      | IHistoryExchangeRatesJsonRequest
      | (Partial<NewHistoryExchangeRatesJsonRequest> & HistoryExchangeRatesJsonRequestFormDefaults),
  ): HistoryExchangeRatesJsonRequestFormRawValue | PartialWithRequiredKeyOf<NewHistoryExchangeRatesJsonRequestFormRawValue> {
    return {
      ...historyExchangeRatesJsonRequest,
      timestamp: historyExchangeRatesJsonRequest.timestamp ? historyExchangeRatesJsonRequest.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
