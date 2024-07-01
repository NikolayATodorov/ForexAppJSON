import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IExchangeRate, NewExchangeRate } from '../exchange-rate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExchangeRate for edit and NewExchangeRateFormGroupInput for create.
 */
type ExchangeRateFormGroupInput = IExchangeRate | PartialWithRequiredKeyOf<NewExchangeRate>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IExchangeRate | NewExchangeRate> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

type ExchangeRateFormRawValue = FormValueOf<IExchangeRate>;

type NewExchangeRateFormRawValue = FormValueOf<NewExchangeRate>;

type ExchangeRateFormDefaults = Pick<NewExchangeRate, 'id' | 'timestamp'>;

type ExchangeRateFormGroupContent = {
  id: FormControl<ExchangeRateFormRawValue['id'] | NewExchangeRate['id']>;
  base: FormControl<ExchangeRateFormRawValue['base']>;
  timestamp: FormControl<ExchangeRateFormRawValue['timestamp']>;
  usd: FormControl<ExchangeRateFormRawValue['usd']>;
  gbp: FormControl<ExchangeRateFormRawValue['gbp']>;
  chf: FormControl<ExchangeRateFormRawValue['chf']>;
};

export type ExchangeRateFormGroup = FormGroup<ExchangeRateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExchangeRateFormService {
  createExchangeRateFormGroup(exchangeRate: ExchangeRateFormGroupInput = { id: null }): ExchangeRateFormGroup {
    const exchangeRateRawValue = this.convertExchangeRateToExchangeRateRawValue({
      ...this.getFormDefaults(),
      ...exchangeRate,
    });
    return new FormGroup<ExchangeRateFormGroupContent>({
      id: new FormControl(
        { value: exchangeRateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      base: new FormControl(exchangeRateRawValue.base, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(3)],
      }),
      timestamp: new FormControl(exchangeRateRawValue.timestamp, {
        validators: [Validators.required],
      }),
      usd: new FormControl(exchangeRateRawValue.usd),
      gbp: new FormControl(exchangeRateRawValue.gbp),
      chf: new FormControl(exchangeRateRawValue.chf),
    });
  }

  getExchangeRate(form: ExchangeRateFormGroup): IExchangeRate | NewExchangeRate {
    return this.convertExchangeRateRawValueToExchangeRate(form.getRawValue() as ExchangeRateFormRawValue | NewExchangeRateFormRawValue);
  }

  resetForm(form: ExchangeRateFormGroup, exchangeRate: ExchangeRateFormGroupInput): void {
    const exchangeRateRawValue = this.convertExchangeRateToExchangeRateRawValue({ ...this.getFormDefaults(), ...exchangeRate });
    form.reset(
      {
        ...exchangeRateRawValue,
        id: { value: exchangeRateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ExchangeRateFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      timestamp: currentTime,
    };
  }

  private convertExchangeRateRawValueToExchangeRate(
    rawExchangeRate: ExchangeRateFormRawValue | NewExchangeRateFormRawValue,
  ): IExchangeRate | NewExchangeRate {
    return {
      ...rawExchangeRate,
      timestamp: dayjs(rawExchangeRate.timestamp, DATE_TIME_FORMAT),
    };
  }

  private convertExchangeRateToExchangeRateRawValue(
    exchangeRate: IExchangeRate | (Partial<NewExchangeRate> & ExchangeRateFormDefaults),
  ): ExchangeRateFormRawValue | PartialWithRequiredKeyOf<NewExchangeRateFormRawValue> {
    return {
      ...exchangeRate,
      timestamp: exchangeRate.timestamp ? exchangeRate.timestamp.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
