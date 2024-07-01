import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { HistoryExchangeRatesJsonRequestComponent } from './list/history-exchange-rates-json-request.component';
import { HistoryExchangeRatesJsonRequestDetailComponent } from './detail/history-exchange-rates-json-request-detail.component';
import { HistoryExchangeRatesJsonRequestUpdateComponent } from './update/history-exchange-rates-json-request-update.component';
import HistoryExchangeRatesJsonRequestResolve from './route/history-exchange-rates-json-request-routing-resolve.service';

const historyExchangeRatesJsonRequestRoute: Routes = [
  {
    path: '',
    component: HistoryExchangeRatesJsonRequestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HistoryExchangeRatesJsonRequestDetailComponent,
    resolve: {
      historyExchangeRatesJsonRequest: HistoryExchangeRatesJsonRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HistoryExchangeRatesJsonRequestUpdateComponent,
    resolve: {
      historyExchangeRatesJsonRequest: HistoryExchangeRatesJsonRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HistoryExchangeRatesJsonRequestUpdateComponent,
    resolve: {
      historyExchangeRatesJsonRequest: HistoryExchangeRatesJsonRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default historyExchangeRatesJsonRequestRoute;
