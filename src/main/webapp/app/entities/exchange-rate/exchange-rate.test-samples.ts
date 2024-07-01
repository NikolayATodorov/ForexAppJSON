import dayjs from 'dayjs/esm';

import { IExchangeRate, NewExchangeRate } from './exchange-rate.model';

export const sampleWithRequiredData: IExchangeRate = {
  id: 19933,
  base: 'sus',
  timestamp: dayjs('2024-06-27T19:59'),
};

export const sampleWithPartialData: IExchangeRate = {
  id: 19385,
  base: 'fam',
  timestamp: dayjs('2024-06-27T11:04'),
};

export const sampleWithFullData: IExchangeRate = {
  id: 25667,
  base: 'cau',
  timestamp: dayjs('2024-06-27T21:03'),
  usd: 10317.73,
  gbp: 32128.08,
  chf: 13478.02,
};

export const sampleWithNewData: NewExchangeRate = {
  base: 'sti',
  timestamp: dayjs('2024-06-27T22:27'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
