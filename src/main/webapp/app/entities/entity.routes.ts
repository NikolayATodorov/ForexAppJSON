import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'exchange-rate',
    data: { pageTitle: 'ExchangeRates' },
    loadChildren: () => import('./exchange-rate/exchange-rate.routes'),
  },
  {
    path: 'current-exchange-rates-json-request',
    data: { pageTitle: 'CurrentExchangeRatesJsonRequests' },
    loadChildren: () => import('./current-exchange-rates-json-request/current-exchange-rates-json-request.routes'),
  },
  {
    path: 'history-exchange-rates-json-request',
    data: { pageTitle: 'HistoryExchangeRatesJsonRequests' },
    loadChildren: () => import('./history-exchange-rates-json-request/history-exchange-rates-json-request.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
