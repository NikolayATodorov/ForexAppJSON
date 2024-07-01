import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';
import { CurrentExchangeRatesJsonRequestService } from '../service/current-exchange-rates-json-request.service';

const currentExchangeRatesJsonRequestResolve = (route: ActivatedRouteSnapshot): Observable<null | ICurrentExchangeRatesJsonRequest> => {
  const id = route.params['id'];
  if (id) {
    return inject(CurrentExchangeRatesJsonRequestService)
      .find(id)
      .pipe(
        mergeMap((currentExchangeRatesJsonRequest: HttpResponse<ICurrentExchangeRatesJsonRequest>) => {
          if (currentExchangeRatesJsonRequest.body) {
            return of(currentExchangeRatesJsonRequest.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default currentExchangeRatesJsonRequestResolve;
