import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ExchangeRateComponent } from './list/exchange-rate.component';
import { ExchangeRateDetailComponent } from './detail/exchange-rate-detail.component';
import { ExchangeRateUpdateComponent } from './update/exchange-rate-update.component';
import ExchangeRateResolve from './route/exchange-rate-routing-resolve.service';

const exchangeRateRoute: Routes = [
  {
    path: '',
    component: ExchangeRateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExchangeRateDetailComponent,
    resolve: {
      exchangeRate: ExchangeRateResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExchangeRateUpdateComponent,
    resolve: {
      exchangeRate: ExchangeRateResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExchangeRateUpdateComponent,
    resolve: {
      exchangeRate: ExchangeRateResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default exchangeRateRoute;
