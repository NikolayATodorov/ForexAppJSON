import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';
import { HistoryExchangeRatesJsonRequestService } from '../service/history-exchange-rates-json-request.service';

import historyExchangeRatesJsonRequestResolve from './history-exchange-rates-json-request-routing-resolve.service';

describe('HistoryExchangeRatesJsonRequest routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: HistoryExchangeRatesJsonRequestService;
  let resultHistoryExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(HistoryExchangeRatesJsonRequestService);
    resultHistoryExchangeRatesJsonRequest = undefined;
  });

  describe('resolve', () => {
    it('should return IHistoryExchangeRatesJsonRequest returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        historyExchangeRatesJsonRequestResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHistoryExchangeRatesJsonRequest = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultHistoryExchangeRatesJsonRequest).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        historyExchangeRatesJsonRequestResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHistoryExchangeRatesJsonRequest = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHistoryExchangeRatesJsonRequest).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IHistoryExchangeRatesJsonRequest>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        historyExchangeRatesJsonRequestResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHistoryExchangeRatesJsonRequest = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultHistoryExchangeRatesJsonRequest).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
