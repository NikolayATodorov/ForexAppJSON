import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { HistoryExchangeRatesJsonRequestService } from '../service/history-exchange-rates-json-request.service';
import { IHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';
import { HistoryExchangeRatesJsonRequestFormService } from './history-exchange-rates-json-request-form.service';

import { HistoryExchangeRatesJsonRequestUpdateComponent } from './history-exchange-rates-json-request-update.component';

describe('HistoryExchangeRatesJsonRequest Management Update Component', () => {
  let comp: HistoryExchangeRatesJsonRequestUpdateComponent;
  let fixture: ComponentFixture<HistoryExchangeRatesJsonRequestUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let historyExchangeRatesJsonRequestFormService: HistoryExchangeRatesJsonRequestFormService;
  let historyExchangeRatesJsonRequestService: HistoryExchangeRatesJsonRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HistoryExchangeRatesJsonRequestUpdateComponent],
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
      .overrideTemplate(HistoryExchangeRatesJsonRequestUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HistoryExchangeRatesJsonRequestUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    historyExchangeRatesJsonRequestFormService = TestBed.inject(HistoryExchangeRatesJsonRequestFormService);
    historyExchangeRatesJsonRequestService = TestBed.inject(HistoryExchangeRatesJsonRequestService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest = { id: 456 };

      activatedRoute.data = of({ historyExchangeRatesJsonRequest });
      comp.ngOnInit();

      expect(comp.historyExchangeRatesJsonRequest).toEqual(historyExchangeRatesJsonRequest);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistoryExchangeRatesJsonRequest>>();
      const historyExchangeRatesJsonRequest = { id: 123 };
      jest
        .spyOn(historyExchangeRatesJsonRequestFormService, 'getHistoryExchangeRatesJsonRequest')
        .mockReturnValue(historyExchangeRatesJsonRequest);
      jest.spyOn(historyExchangeRatesJsonRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historyExchangeRatesJsonRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historyExchangeRatesJsonRequest }));
      saveSubject.complete();

      // THEN
      expect(historyExchangeRatesJsonRequestFormService.getHistoryExchangeRatesJsonRequest).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(historyExchangeRatesJsonRequestService.update).toHaveBeenCalledWith(expect.objectContaining(historyExchangeRatesJsonRequest));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistoryExchangeRatesJsonRequest>>();
      const historyExchangeRatesJsonRequest = { id: 123 };
      jest.spyOn(historyExchangeRatesJsonRequestFormService, 'getHistoryExchangeRatesJsonRequest').mockReturnValue({ id: null });
      jest.spyOn(historyExchangeRatesJsonRequestService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historyExchangeRatesJsonRequest: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historyExchangeRatesJsonRequest }));
      saveSubject.complete();

      // THEN
      expect(historyExchangeRatesJsonRequestFormService.getHistoryExchangeRatesJsonRequest).toHaveBeenCalled();
      expect(historyExchangeRatesJsonRequestService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistoryExchangeRatesJsonRequest>>();
      const historyExchangeRatesJsonRequest = { id: 123 };
      jest.spyOn(historyExchangeRatesJsonRequestService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historyExchangeRatesJsonRequest });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(historyExchangeRatesJsonRequestService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
