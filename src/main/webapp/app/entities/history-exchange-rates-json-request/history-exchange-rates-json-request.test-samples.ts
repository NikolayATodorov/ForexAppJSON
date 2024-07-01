import dayjs from 'dayjs/esm';

import { IHistoryExchangeRatesJsonRequest, NewHistoryExchangeRatesJsonRequest } from './history-exchange-rates-json-request.model';

export const sampleWithRequiredData: IHistoryExchangeRatesJsonRequest = {
  id: 7396,
  requestId: 'pastry',
  timestamp: dayjs('2024-06-27T22:32'),
  client: 'instead yuck freon',
  currency: 'les',
  period: 26595,
};

export const sampleWithPartialData: IHistoryExchangeRatesJsonRequest = {
  id: 13683,
  requestId: 'who',
  timestamp: dayjs('2024-06-27T23:44'),
  client: 'supposing',
  currency: 'dis',
  period: 10263,
};

export const sampleWithFullData: IHistoryExchangeRatesJsonRequest = {
  id: 3143,
  requestId: 'safely lest',
  timestamp: dayjs('2024-06-27T18:14'),
  client: 'baptise if summit',
  currency: 'dic',
  period: 13690,
};

export const sampleWithNewData: NewHistoryExchangeRatesJsonRequest = {
  requestId: 'joyously river when',
  timestamp: dayjs('2024-06-28T04:50'),
  client: 'underneath uh-huh concept',
  currency: 'can',
  period: 15609,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
