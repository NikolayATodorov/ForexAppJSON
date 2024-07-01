import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../history-exchange-rates-json-request.test-samples';

import { HistoryExchangeRatesJsonRequestFormService } from './history-exchange-rates-json-request-form.service';

describe('HistoryExchangeRatesJsonRequest Form Service', () => {
  let service: HistoryExchangeRatesJsonRequestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistoryExchangeRatesJsonRequestFormService);
  });

  describe('Service methods', () => {
    describe('createHistoryExchangeRatesJsonRequestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHistoryExchangeRatesJsonRequestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestId: expect.any(Object),
            timestamp: expect.any(Object),
            client: expect.any(Object),
            currency: expect.any(Object),
            period: expect.any(Object),
          }),
        );
      });

      it('passing IHistoryExchangeRatesJsonRequest should create a new form with FormGroup', () => {
        const formGroup = service.createHistoryExchangeRatesJsonRequestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestId: expect.any(Object),
            timestamp: expect.any(Object),
            client: expect.any(Object),
            currency: expect.any(Object),
            period: expect.any(Object),
          }),
        );
      });
    });

    describe('getHistoryExchangeRatesJsonRequest', () => {
      it('should return NewHistoryExchangeRatesJsonRequest for default HistoryExchangeRatesJsonRequest initial value', () => {
        const formGroup = service.createHistoryExchangeRatesJsonRequestFormGroup(sampleWithNewData);

        const historyExchangeRatesJsonRequest = service.getHistoryExchangeRatesJsonRequest(formGroup) as any;

        expect(historyExchangeRatesJsonRequest).toMatchObject(sampleWithNewData);
      });

      it('should return NewHistoryExchangeRatesJsonRequest for empty HistoryExchangeRatesJsonRequest initial value', () => {
        const formGroup = service.createHistoryExchangeRatesJsonRequestFormGroup();

        const historyExchangeRatesJsonRequest = service.getHistoryExchangeRatesJsonRequest(formGroup) as any;

        expect(historyExchangeRatesJsonRequest).toMatchObject({});
      });

      it('should return IHistoryExchangeRatesJsonRequest', () => {
        const formGroup = service.createHistoryExchangeRatesJsonRequestFormGroup(sampleWithRequiredData);

        const historyExchangeRatesJsonRequest = service.getHistoryExchangeRatesJsonRequest(formGroup) as any;

        expect(historyExchangeRatesJsonRequest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHistoryExchangeRatesJsonRequest should not enable id FormControl', () => {
        const formGroup = service.createHistoryExchangeRatesJsonRequestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHistoryExchangeRatesJsonRequest should disable id FormControl', () => {
        const formGroup = service.createHistoryExchangeRatesJsonRequestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
