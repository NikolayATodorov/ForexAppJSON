import dayjs from 'dayjs/esm';

export interface IExchangeRate {
  id: number;
  base?: string | null;
  timestamp?: dayjs.Dayjs | null;
  usd?: number | null;
  gbp?: number | null;
  chf?: number | null;
}

export type NewExchangeRate = Omit<IExchangeRate, 'id'> & { id: null };
