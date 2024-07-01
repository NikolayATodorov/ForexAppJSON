import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExchangeRate } from '../exchange-rate.model';
import { ExchangeRateService } from '../service/exchange-rate.service';

const exchangeRateResolve = (route: ActivatedRouteSnapshot): Observable<null | IExchangeRate> => {
  const id = route.params['id'];
  if (id) {
    return inject(ExchangeRateService)
      .find(id)
      .pipe(
        mergeMap((exchangeRate: HttpResponse<IExchangeRate>) => {
          if (exchangeRate.body) {
            return of(exchangeRate.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default exchangeRateResolve;
