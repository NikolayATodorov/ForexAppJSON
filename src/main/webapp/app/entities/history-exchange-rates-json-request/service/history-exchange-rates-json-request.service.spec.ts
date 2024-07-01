import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../history-exchange-rates-json-request.test-samples';

import { HistoryExchangeRatesJsonRequestService, RestHistoryExchangeRatesJsonRequest } from './history-exchange-rates-json-request.service';

const requireRestSample: RestHistoryExchangeRatesJsonRequest = {
  ...sampleWithRequiredData,
  timestamp: sampleWithRequiredData.timestamp?.toJSON(),
};

describe('HistoryExchangeRatesJsonRequest Service', () => {
  let service: HistoryExchangeRatesJsonRequestService;
  let httpMock: HttpTestingController;
  let expectedResult: IHistoryExchangeRatesJsonRequest | IHistoryExchangeRatesJsonRequest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HistoryExchangeRatesJsonRequestService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a HistoryExchangeRatesJsonRequest', () => {
      const historyExchangeRatesJsonRequest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(historyExchangeRatesJsonRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HistoryExchangeRatesJsonRequest', () => {
      const historyExchangeRatesJsonRequest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(historyExchangeRatesJsonRequest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HistoryExchangeRatesJsonRequest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HistoryExchangeRatesJsonRequest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HistoryExchangeRatesJsonRequest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHistoryExchangeRatesJsonRequestToCollectionIfMissing', () => {
      it('should add a HistoryExchangeRatesJsonRequest to an empty array', () => {
        const historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest = sampleWithRequiredData;
        expectedResult = service.addHistoryExchangeRatesJsonRequestToCollectionIfMissing([], historyExchangeRatesJsonRequest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historyExchangeRatesJsonRequest);
      });

      it('should not add a HistoryExchangeRatesJsonRequest to an array that contains it', () => {
        const historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest = sampleWithRequiredData;
        const historyExchangeRatesJsonRequestCollection: IHistoryExchangeRatesJsonRequest[] = [
          {
            ...historyExchangeRatesJsonRequest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHistoryExchangeRatesJsonRequestToCollectionIfMissing(
          historyExchangeRatesJsonRequestCollection,
          historyExchangeRatesJsonRequest,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HistoryExchangeRatesJsonRequest to an array that doesn't contain it", () => {
        const historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest = sampleWithRequiredData;
        const historyExchangeRatesJsonRequestCollection: IHistoryExchangeRatesJsonRequest[] = [sampleWithPartialData];
        expectedResult = service.addHistoryExchangeRatesJsonRequestToCollectionIfMissing(
          historyExchangeRatesJsonRequestCollection,
          historyExchangeRatesJsonRequest,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historyExchangeRatesJsonRequest);
      });

      it('should add only unique HistoryExchangeRatesJsonRequest to an array', () => {
        const historyExchangeRatesJsonRequestArray: IHistoryExchangeRatesJsonRequest[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const historyExchangeRatesJsonRequestCollection: IHistoryExchangeRatesJsonRequest[] = [sampleWithRequiredData];
        expectedResult = service.addHistoryExchangeRatesJsonRequestToCollectionIfMissing(
          historyExchangeRatesJsonRequestCollection,
          ...historyExchangeRatesJsonRequestArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest = sampleWithRequiredData;
        const historyExchangeRatesJsonRequest2: IHistoryExchangeRatesJsonRequest = sampleWithPartialData;
        expectedResult = service.addHistoryExchangeRatesJsonRequestToCollectionIfMissing(
          [],
          historyExchangeRatesJsonRequest,
          historyExchangeRatesJsonRequest2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(historyExchangeRatesJsonRequest);
        expect(expectedResult).toContain(historyExchangeRatesJsonRequest2);
      });

      it('should accept null and undefined values', () => {
        const historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest = sampleWithRequiredData;
        expectedResult = service.addHistoryExchangeRatesJsonRequestToCollectionIfMissing(
          [],
          null,
          historyExchangeRatesJsonRequest,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(historyExchangeRatesJsonRequest);
      });

      it('should return initial array if no HistoryExchangeRatesJsonRequest is added', () => {
        const historyExchangeRatesJsonRequestCollection: IHistoryExchangeRatesJsonRequest[] = [sampleWithRequiredData];
        expectedResult = service.addHistoryExchangeRatesJsonRequestToCollectionIfMissing(
          historyExchangeRatesJsonRequestCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(historyExchangeRatesJsonRequestCollection);
      });
    });

    describe('compareHistoryExchangeRatesJsonRequest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHistoryExchangeRatesJsonRequest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHistoryExchangeRatesJsonRequest(entity1, entity2);
        const compareResult2 = service.compareHistoryExchangeRatesJsonRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHistoryExchangeRatesJsonRequest(entity1, entity2);
        const compareResult2 = service.compareHistoryExchangeRatesJsonRequest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHistoryExchangeRatesJsonRequest(entity1, entity2);
        const compareResult2 = service.compareHistoryExchangeRatesJsonRequest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
