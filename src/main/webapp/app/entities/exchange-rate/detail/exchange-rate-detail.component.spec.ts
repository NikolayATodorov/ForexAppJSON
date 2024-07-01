import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ExchangeRateDetailComponent } from './exchange-rate-detail.component';

describe('ExchangeRate Management Detail Component', () => {
  let comp: ExchangeRateDetailComponent;
  let fixture: ComponentFixture<ExchangeRateDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExchangeRateDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ExchangeRateDetailComponent,
              resolve: { exchangeRate: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ExchangeRateDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExchangeRateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load exchangeRate on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ExchangeRateDetailComponent);

      // THEN
      expect(instance.exchangeRate()).toEqual(expect.objectContaining({ id: 123 }));
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
