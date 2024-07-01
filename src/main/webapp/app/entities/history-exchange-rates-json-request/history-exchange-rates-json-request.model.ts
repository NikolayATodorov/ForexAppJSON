import dayjs from 'dayjs/esm';

export interface IHistoryExchangeRatesJsonRequest {
  id: number;
  requestId?: string | null;
  timestamp?: dayjs.Dayjs | null;
  client?: string | null;
  currency?: string | null;
  period?: number | null;
}

export type NewHistoryExchangeRatesJsonRequest = Omit<IHistoryExchangeRatesJsonRequest, 'id'> & { id: null };
