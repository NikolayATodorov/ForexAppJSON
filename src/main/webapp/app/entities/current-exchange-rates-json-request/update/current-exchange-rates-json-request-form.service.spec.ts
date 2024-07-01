import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../current-exchange-rates-json-request.test-samples';

import { CurrentExchangeRatesJsonRequestFormService } from './current-exchange-rates-json-request-form.service';

describe('CurrentExchangeRatesJsonRequest Form Service', () => {
  let service: CurrentExchangeRatesJsonRequestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CurrentExchangeRatesJsonRequestFormService);
  });

  describe('Service methods', () => {
    describe('createCurrentExchangeRatesJsonRequestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCurrentExchangeRatesJsonRequestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestId: expect.any(Object),
            timestamp: expect.any(Object),
            client: expect.any(Object),
            currency: expect.any(Object),
          }),
        );
      });

      it('passing ICurrentExchangeRatesJsonRequest should create a new form with FormGroup', () => {
        const formGroup = service.createCurrentExchangeRatesJsonRequestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestId: expect.any(Object),
            timestamp: expect.any(Object),
            client: expect.any(Object),
            currency: expect.any(Object),
          }),
        );
      });
    });

    describe('getCurrentExchangeRatesJsonRequest', () => {
      it('should return NewCurrentExchangeRatesJsonRequest for default CurrentExchangeRatesJsonRequest initial value', () => {
        const formGroup = service.createCurrentExchangeRatesJsonRequestFormGroup(sampleWithNewData);

        const currentExchangeRatesJsonRequest = service.getCurrentExchangeRatesJsonRequest(formGroup) as any;

        expect(currentExchangeRatesJsonRequest).toMatchObject(sampleWithNewData);
      });

      it('should return NewCurrentExchangeRatesJsonRequest for empty CurrentExchangeRatesJsonRequest initial value', () => {
        const formGroup = service.createCurrentExchangeRatesJsonRequestFormGroup();

        const currentExchangeRatesJsonRequest = service.getCurrentExchangeRatesJsonRequest(formGroup) as any;

        expect(currentExchangeRatesJsonRequest).toMatchObject({});
      });

      it('should return ICurrentExchangeRatesJsonRequest', () => {
        const formGroup = service.createCurrentExchangeRatesJsonRequestFormGroup(sampleWithRequiredData);

        const currentExchangeRatesJsonRequest = service.getCurrentExchangeRatesJsonRequest(formGroup) as any;

        expect(currentExchangeRatesJsonRequest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICurrentExchangeRatesJsonRequest should not enable id FormControl', () => {
        const formGroup = service.createCurrentExchangeRatesJsonRequestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCurrentExchangeRatesJsonRequest should disable id FormControl', () => {
        const formGroup = service.createCurrentExchangeRatesJsonRequestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
