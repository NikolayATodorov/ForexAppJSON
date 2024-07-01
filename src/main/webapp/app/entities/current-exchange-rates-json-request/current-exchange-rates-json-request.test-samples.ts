import dayjs from 'dayjs/esm';

import { ICurrentExchangeRatesJsonRequest, NewCurrentExchangeRatesJsonRequest } from './current-exchange-rates-json-request.model';

export const sampleWithRequiredData: ICurrentExchangeRatesJsonRequest = {
  id: 8025,
  requestId: 'hearty spell',
  timestamp: dayjs('2024-06-28T00:34'),
  client: 'minus properly yuck',
  currency: 'on ',
};

export const sampleWithPartialData: ICurrentExchangeRatesJsonRequest = {
  id: 29117,
  requestId: 'phew athletics',
  timestamp: dayjs('2024-06-27T17:11'),
  client: 'pastor after scrawl',
  currency: 'lik',
};

export const sampleWithFullData: ICurrentExchangeRatesJsonRequest = {
  id: 2037,
  requestId: 'lest helplessly affectionate',
  timestamp: dayjs('2024-06-28T00:14'),
  client: 'nor',
  currency: 'wel',
};

export const sampleWithNewData: NewCurrentExchangeRatesJsonRequest = {
  requestId: 'whenever what',
  timestamp: dayjs('2024-06-28T02:17'),
  client: 'objectify hm',
  currency: 'whe',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
