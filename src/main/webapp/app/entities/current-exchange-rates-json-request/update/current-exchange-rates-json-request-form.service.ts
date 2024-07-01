import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICurrentExchangeRatesJsonRequest, NewCurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICurrentExchangeRatesJsonRequest for edit and NewCurrentExchangeRatesJsonRequestFormGroupInput for create.
 */
type CurrentExchangeRatesJsonRequestFormGroupInput =
  | ICurrentExchangeRatesJsonRequest
  | PartialWithRequiredKeyOf<NewCurrentExchangeRatesJsonRequest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICurrentExchangeRatesJsonRequest | NewCurrentExchangeRatesJsonRequest> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type CurrentExchangeRatesJsonRequestFormRawValue = FormValueOf<ICurrentExchangeRatesJsonRequest>;

type NewCurrentExchangeRatesJsonRequestFormRawValue = FormValueOf<NewCurrentExchangeRatesJsonRequest>;

type CurrentExchangeRatesJsonRequestFormDefaults = Pick<NewCurrentExchangeRatesJsonRequest, 'id' | 'timestamp'>;

type CurrentExchangeRatesJsonRequestFormGroupContent = {
  id: FormControl<CurrentExchangeRatesJsonRequestFormRawValue['id'] | NewCurrentExchangeRatesJsonRequest['id']>;
  requestId: FormControl<CurrentExchangeRatesJsonRequestFormRawValue['requestId']>;
  timestamp: FormControl<CurrentExchangeRatesJsonRequestFormRawValue['timestamp']>;
  client: FormControl<CurrentExchangeRatesJsonRequestFormRawValue['client']>;
  currency: FormControl<CurrentExchangeRatesJsonRequestFormRawValue['currency']>;
};

export type CurrentExchangeRatesJsonRequestFormGroup = FormGroup<CurrentExchangeRatesJsonRequestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CurrentExchangeRatesJsonRequestFormService {
  createCurrentExchangeRatesJsonRequestFormGroup(
    currentExchangeRatesJsonRequest: CurrentExchangeRatesJsonRequestFormGroupInput = { id: null },
  ): CurrentExchangeRatesJsonRequestFormGroup {
    const currentExchangeRatesJsonRequestRawValue = this.convertCurrentExchangeRatesJsonRequestToCurrentExchangeRatesJsonRequestRawValue({
      ...this.getFormDefaults(),
      ...currentExchangeRatesJsonRequest,
    });
    return new FormGroup<CurrentExchangeRatesJsonRequestFormGroupContent>({
      id: new FormControl(
        { value: currentExchangeRatesJsonRequestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      requestId: new FormControl(currentExchangeRatesJsonRequestRawValue.requestId, {
        validators: [Validators.required],
      }),
      timestamp: new FormControl(currentExchangeRatesJsonRequestRawValue.timestamp, {
        validators: [Validators.required],
      }),
      client: new FormControl(currentExchangeRatesJsonRequestRawValue.client, {
        validators: [Validators.required],
      }),
      currency: new FormControl(currentExchangeRatesJsonRequestRawValue.currency, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(3)],
      }),
    });
  }

  getCurrentExchangeRatesJsonRequest(
    form: CurrentExchangeRatesJsonRequestFormGroup,
  ): ICurrentExchangeRatesJsonRequest | NewCurrentExchangeRatesJsonRequest {
    return this.convertCurrentExchangeRatesJsonRequestRawValueToCurrentExchangeRatesJsonRequest(
      form.getRawValue() as CurrentExchangeRatesJsonRequestFormRawValue | NewCurrentExchangeRatesJsonRequestFormRawValue,
    );
  }

  resetForm(
    form: CurrentExchangeRatesJsonRequestFormGroup,
    currentExchangeRatesJsonRequest: CurrentExchangeRatesJsonRequestFormGroupInput,
  ): void {
    const currentExchangeRatesJsonRequestRawValue = this.convertCurrentExchangeRatesJsonRequestToCurrentExchangeRatesJsonRequestRawValue({
      ...this.getFormDefaults(),
      ...currentExchangeRatesJsonRequest,
    });
    form.reset(
      {
        ...currentExchangeRatesJsonRequestRawValue,
        id: { value: currentExchangeRatesJsonRequestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CurrentExchangeRatesJsonRequestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertCurrentExchangeRatesJsonRequestRawValueToCurrentExchangeRatesJsonRequest(
    rawCurrentExchangeRatesJsonRequest: CurrentExchangeRatesJsonRequestFormRawValue | NewCurrentExchangeRatesJsonRequestFormRawValue,
  ): ICurrentExchangeRatesJsonRequest | NewCurrentExchangeRatesJsonRequest {
    return {
      ...rawCurrentExchangeRatesJsonRequest,
      timestamp: dayjs(rawCurrentExchangeRatesJsonRequest.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertCurrentExchangeRatesJsonRequestToCurrentExchangeRatesJsonRequestRawValue(
    currentExchangeRatesJsonRequest:
      | ICurrentExchangeRatesJsonRequest
      | (Partial<NewCurrentExchangeRatesJsonRequest> & CurrentExchangeRatesJsonRequestFormDefaults),
  ): CurrentExchangeRatesJsonRequestFormRawValue | PartialWithRequiredKeyOf<NewCurrentExchangeRatesJsonRequestFormRawValue> {
    return {
      ...currentExchangeRatesJsonRequest,
      timestamp: currentExchangeRatesJsonRequest.timestamp ? currentExchangeRatesJsonRequest.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
