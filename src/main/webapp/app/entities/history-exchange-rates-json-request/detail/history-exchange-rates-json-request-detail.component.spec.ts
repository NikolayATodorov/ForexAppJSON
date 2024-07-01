import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { HistoryExchangeRatesJsonRequestDetailComponent } from './history-exchange-rates-json-request-detail.component';

describe('HistoryExchangeRatesJsonRequest Management Detail Component', () => {
  let comp: HistoryExchangeRatesJsonRequestDetailComponent;
  let fixture: ComponentFixture<HistoryExchangeRatesJsonRequestDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoryExchangeRatesJsonRequestDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: HistoryExchangeRatesJsonRequestDetailComponent,
              resolve: { historyExchangeRatesJsonRequest: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HistoryExchangeRatesJsonRequestDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryExchangeRatesJsonRequestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load historyExchangeRatesJsonRequest on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HistoryExchangeRatesJsonRequestDetailComponent);

      // THEN
      expect(instance.historyExchangeRatesJsonRequest()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
