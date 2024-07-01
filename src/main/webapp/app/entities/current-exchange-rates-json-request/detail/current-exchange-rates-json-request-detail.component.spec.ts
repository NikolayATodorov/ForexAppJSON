import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CurrentExchangeRatesJsonRequestDetailComponent } from './current-exchange-rates-json-request-detail.component';

describe('CurrentExchangeRatesJsonRequest Management Detail Component', () => {
  let comp: CurrentExchangeRatesJsonRequestDetailComponent;
  let fixture: ComponentFixture<CurrentExchangeRatesJsonRequestDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CurrentExchangeRatesJsonRequestDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CurrentExchangeRatesJsonRequestDetailComponent,
              resolve: { currentExchangeRatesJsonRequest: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CurrentExchangeRatesJsonRequestDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrentExchangeRatesJsonRequestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load currentExchangeRatesJsonRequest on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CurrentExchangeRatesJsonRequestDetailComponent);

      // THEN
      expect(instance.currentExchangeRatesJsonRequest()).toEqual(expect.objectContaining({ id: 123 }));
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
