import dayjs from 'dayjs/esm';

export interface ICurrentExchangeRatesJsonRequest {
  id: number;
  requestId?: string | null;
  timestamp?: dayjs.Dayjs | null;
  client?: string | null;
  currency?: string | null;
}

export type NewCurrentExchangeRatesJsonRequest = Omit<ICurrentExchangeRatesJsonRequest, 'id'> & { id: null };
