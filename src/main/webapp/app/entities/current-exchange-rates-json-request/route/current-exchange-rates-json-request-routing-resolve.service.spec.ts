import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';
import { CurrentExchangeRatesJsonRequestService } from '../service/current-exchange-rates-json-request.service';

import currentExchangeRatesJsonRequestResolve from './current-exchange-rates-json-request-routing-resolve.service';

describe('CurrentExchangeRatesJsonRequest routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CurrentExchangeRatesJsonRequestService;
  let resultCurrentExchangeRatesJsonRequest: ICurrentExchangeRatesJsonRequest | null | undefined;

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
    service = TestBed.inject(CurrentExchangeRatesJsonRequestService);
    resultCurrentExchangeRatesJsonRequest = undefined;
  });

  describe('resolve', () => {
    it('should return ICurrentExchangeRatesJsonRequest returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        currentExchangeRatesJsonRequestResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCurrentExchangeRatesJsonRequest = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCurrentExchangeRatesJsonRequest).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        currentExchangeRatesJsonRequestResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCurrentExchangeRatesJsonRequest = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCurrentExchangeRatesJsonRequest).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICurrentExchangeRatesJsonRequest>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        currentExchangeRatesJsonRequestResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCurrentExchangeRatesJsonRequest = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCurrentExchangeRatesJsonRequest).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
