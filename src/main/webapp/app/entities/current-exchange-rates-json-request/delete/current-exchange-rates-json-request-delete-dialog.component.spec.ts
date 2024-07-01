jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CurrentExchangeRatesJsonRequestService } from '../service/current-exchange-rates-json-request.service';

import { CurrentExchangeRatesJsonRequestDeleteDialogComponent } from './current-exchange-rates-json-request-delete-dialog.component';

describe('CurrentExchangeRatesJsonRequest Management Delete Component', () => {
  let comp: CurrentExchangeRatesJsonRequestDeleteDialogComponent;
  let fixture: ComponentFixture<CurrentExchangeRatesJsonRequestDeleteDialogComponent>;
  let service: CurrentExchangeRatesJsonRequestService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CurrentExchangeRatesJsonRequestDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(CurrentExchangeRatesJsonRequestDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CurrentExchangeRatesJsonRequestDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CurrentExchangeRatesJsonRequestService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
