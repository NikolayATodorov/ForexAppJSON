import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CurrentExchangeRatesJsonRequestService } from '../service/current-exchange-rates-json-request.service';
import { ICurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';
import { CurrentExchangeRatesJsonRequestFormService } from './current-exchange-rates-json-request-form.service';

import { CurrentExchangeRatesJsonRequestUpdateComponent } from './current-exchange-rates-json-request-update.component';

describe('CurrentExchangeRatesJsonRequest Management Update Component', () => {
  let comp: CurrentExchangeRatesJsonRequestUpdateComponent;
  let fixture: ComponentFixture<CurrentExchangeRatesJsonRequestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let currentExchangeRatesJsonRequestFormService: CurrentExchangeRatesJsonRequestFormService;
  let currentExchangeRatesJsonRequestService: CurrentExchangeRatesJsonRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CurrentExchangeRatesJsonRequestUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CurrentExchangeRatesJsonRequestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CurrentExchangeRatesJsonRequestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    currentExchangeRatesJsonRequestFormService = TestBed.inject(CurrentExchangeRatesJsonRequestFormService);
    currentExchangeRatesJsonRequestService = TestBed.inject(CurrentExchangeRatesJsonRequestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const currentExchangeRatesJsonRequest: ICurrentExchangeRatesJsonRequest = { id: 456 };

      activatedRoute.data = of({ currentExchangeRatesJsonRequest });
      comp.ngOnInit();

      expect(comp.currentExchangeRatesJsonRequest).toEqual(currentExchangeRatesJsonRequest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrentExchangeRatesJsonRequest>>();
      const currentExchangeRatesJsonRequest = { id: 123 };
      jest
        .spyOn(currentExchangeRatesJsonRequestFormService, 'getCurrentExchangeRatesJsonRequest')
        .mockReturnValue(currentExchangeRatesJsonRequest);
      jest.spyOn(currentExchangeRatesJsonRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currentExchangeRatesJsonRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currentExchangeRatesJsonRequest }));
      saveSubject.complete();

      // THEN
      expect(currentExchangeRatesJsonRequestFormService.getCurrentExchangeRatesJsonRequest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(currentExchangeRatesJsonRequestService.update).toHaveBeenCalledWith(expect.objectContaining(currentExchangeRatesJsonRequest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrentExchangeRatesJsonRequest>>();
      const currentExchangeRatesJsonRequest = { id: 123 };
      jest.spyOn(currentExchangeRatesJsonRequestFormService, 'getCurrentExchangeRatesJsonRequest').mockReturnValue({ id: null });
      jest.spyOn(currentExchangeRatesJsonRequestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currentExchangeRatesJsonRequest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: currentExchangeRatesJsonRequest }));
      saveSubject.complete();

      // THEN
      expect(currentExchangeRatesJsonRequestFormService.getCurrentExchangeRatesJsonRequest).toHaveBeenCalled();
      expect(currentExchangeRatesJsonRequestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICurrentExchangeRatesJsonRequest>>();
      const currentExchangeRatesJsonRequest = { id: 123 };
      jest.spyOn(currentExchangeRatesJsonRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ currentExchangeRatesJsonRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(currentExchangeRatesJsonRequestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
