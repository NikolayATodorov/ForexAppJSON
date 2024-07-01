import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CurrentExchangeRatesJsonRequestComponent } from './list/current-exchange-rates-json-request.component';
import { CurrentExchangeRatesJsonRequestDetailComponent } from './detail/current-exchange-rates-json-request-detail.component';
import { CurrentExchangeRatesJsonRequestUpdateComponent } from './update/current-exchange-rates-json-request-update.component';
import CurrentExchangeRatesJsonRequestResolve from './route/current-exchange-rates-json-request-routing-resolve.service';

const currentExchangeRatesJsonRequestRoute: Routes = [
  {
    path: '',
    component: CurrentExchangeRatesJsonRequestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CurrentExchangeRatesJsonRequestDetailComponent,
    resolve: {
      currentExchangeRatesJsonRequest: CurrentExchangeRatesJsonRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CurrentExchangeRatesJsonRequestUpdateComponent,
    resolve: {
      currentExchangeRatesJsonRequest: CurrentExchangeRatesJsonRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CurrentExchangeRatesJsonRequestUpdateComponent,
    resolve: {
      currentExchangeRatesJsonRequest: CurrentExchangeRatesJsonRequestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default currentExchangeRatesJsonRequestRoute;
