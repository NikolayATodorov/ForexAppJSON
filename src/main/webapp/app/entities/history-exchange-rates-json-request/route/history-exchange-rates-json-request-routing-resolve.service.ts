import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';
import { HistoryExchangeRatesJsonRequestService } from '../service/history-exchange-rates-json-request.service';

const historyExchangeRatesJsonRequestResolve = (route: ActivatedRouteSnapshot): Observable<null | IHistoryExchangeRatesJsonRequest> => {
  const id = route.params['id'];
  if (id) {
    return inject(HistoryExchangeRatesJsonRequestService)
      .find(id)
      .pipe(
        mergeMap((historyExchangeRatesJsonRequest: HttpResponse<IHistoryExchangeRatesJsonRequest>) => {
          if (historyExchangeRatesJsonRequest.body) {
            return of(historyExchangeRatesJsonRequest.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default historyExchangeRatesJsonRequestResolve;
