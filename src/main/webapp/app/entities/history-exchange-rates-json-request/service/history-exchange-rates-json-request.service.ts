import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHistoryExchangeRatesJsonRequest, NewHistoryExchangeRatesJsonRequest } from '../history-exchange-rates-json-request.model';

export type PartialUpdateHistoryExchangeRatesJsonRequest = Partial<IHistoryExchangeRatesJsonRequest> &
  Pick<IHistoryExchangeRatesJsonRequest, 'id'>;

type RestOf<T extends IHistoryExchangeRatesJsonRequest | NewHistoryExchangeRatesJsonRequest> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestHistoryExchangeRatesJsonRequest = RestOf<IHistoryExchangeRatesJsonRequest>;

export type NewRestHistoryExchangeRatesJsonRequest = RestOf<NewHistoryExchangeRatesJsonRequest>;

export type PartialUpdateRestHistoryExchangeRatesJsonRequest = RestOf<PartialUpdateHistoryExchangeRatesJsonRequest>;

export type EntityResponseType = HttpResponse<IHistoryExchangeRatesJsonRequest>;
export type EntityArrayResponseType = HttpResponse<IHistoryExchangeRatesJsonRequest[]>;

@Injectable({ providedIn: 'root' })
export class HistoryExchangeRatesJsonRequestService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/history-exchange-rates-json-requests');

  create(historyExchangeRatesJsonRequest: NewHistoryExchangeRatesJsonRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historyExchangeRatesJsonRequest);
    return this.http
      .post<RestHistoryExchangeRatesJsonRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(historyExchangeRatesJsonRequest: IHistoryExchangeRatesJsonRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historyExchangeRatesJsonRequest);
    return this.http
      .put<RestHistoryExchangeRatesJsonRequest>(
        `${this.resourceUrl}/${this.getHistoryExchangeRatesJsonRequestIdentifier(historyExchangeRatesJsonRequest)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(historyExchangeRatesJsonRequest: PartialUpdateHistoryExchangeRatesJsonRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historyExchangeRatesJsonRequest);
    return this.http
      .patch<RestHistoryExchangeRatesJsonRequest>(
        `${this.resourceUrl}/${this.getHistoryExchangeRatesJsonRequestIdentifier(historyExchangeRatesJsonRequest)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestHistoryExchangeRatesJsonRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestHistoryExchangeRatesJsonRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHistoryExchangeRatesJsonRequestIdentifier(historyExchangeRatesJsonRequest: Pick<IHistoryExchangeRatesJsonRequest, 'id'>): number {
    return historyExchangeRatesJsonRequest.id;
  }

  compareHistoryExchangeRatesJsonRequest(
    o1: Pick<IHistoryExchangeRatesJsonRequest, 'id'> | null,
    o2: Pick<IHistoryExchangeRatesJsonRequest, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getHistoryExchangeRatesJsonRequestIdentifier(o1) === this.getHistoryExchangeRatesJsonRequestIdentifier(o2)
      : o1 === o2;
  }

  addHistoryExchangeRatesJsonRequestToCollectionIfMissing<Type extends Pick<IHistoryExchangeRatesJsonRequest, 'id'>>(
    historyExchangeRatesJsonRequestCollection: Type[],
    ...historyExchangeRatesJsonRequestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const historyExchangeRatesJsonRequests: Type[] = historyExchangeRatesJsonRequestsToCheck.filter(isPresent);
    if (historyExchangeRatesJsonRequests.length > 0) {
      const historyExchangeRatesJsonRequestCollectionIdentifiers = historyExchangeRatesJsonRequestCollection.map(
        historyExchangeRatesJsonRequestItem => this.getHistoryExchangeRatesJsonRequestIdentifier(historyExchangeRatesJsonRequestItem),
      );
      const historyExchangeRatesJsonRequestsToAdd = historyExchangeRatesJsonRequests.filter(historyExchangeRatesJsonRequestItem => {
        const historyExchangeRatesJsonRequestIdentifier =
          this.getHistoryExchangeRatesJsonRequestIdentifier(historyExchangeRatesJsonRequestItem);
        if (historyExchangeRatesJsonRequestCollectionIdentifiers.includes(historyExchangeRatesJsonRequestIdentifier)) {
          return false;
        }
        historyExchangeRatesJsonRequestCollectionIdentifiers.push(historyExchangeRatesJsonRequestIdentifier);
        return true;
      });
      return [...historyExchangeRatesJsonRequestsToAdd, ...historyExchangeRatesJsonRequestCollection];
    }
    return historyExchangeRatesJsonRequestCollection;
  }

  protected convertDateFromClient<
    T extends IHistoryExchangeRatesJsonRequest | NewHistoryExchangeRatesJsonRequest | PartialUpdateHistoryExchangeRatesJsonRequest,
  >(historyExchangeRatesJsonRequest: T): RestOf<T> {
    return {
      ...historyExchangeRatesJsonRequest,
      timestamp: historyExchangeRatesJsonRequest.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restHistoryExchangeRatesJsonRequest: RestHistoryExchangeRatesJsonRequest,
  ): IHistoryExchangeRatesJsonRequest {
    return {
      ...restHistoryExchangeRatesJsonRequest,
      timestamp: restHistoryExchangeRatesJsonRequest.timestamp ? dayjs(restHistoryExchangeRatesJsonRequest.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestHistoryExchangeRatesJsonRequest>,
  ): HttpResponse<IHistoryExchangeRatesJsonRequest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestHistoryExchangeRatesJsonRequest[]>,
  ): HttpResponse<IHistoryExchangeRatesJsonRequest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
