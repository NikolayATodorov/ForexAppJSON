import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICurrentExchangeRatesJsonRequest, NewCurrentExchangeRatesJsonRequest } from '../current-exchange-rates-json-request.model';

export type PartialUpdateCurrentExchangeRatesJsonRequest = Partial<ICurrentExchangeRatesJsonRequest> &
  Pick<ICurrentExchangeRatesJsonRequest, 'id'>;

type RestOf<T extends ICurrentExchangeRatesJsonRequest | NewCurrentExchangeRatesJsonRequest> = Omit<T, 'timestamp'> & {
  timestamp?: string | null;
};

export type RestCurrentExchangeRatesJsonRequest = RestOf<ICurrentExchangeRatesJsonRequest>;

export type NewRestCurrentExchangeRatesJsonRequest = RestOf<NewCurrentExchangeRatesJsonRequest>;

export type PartialUpdateRestCurrentExchangeRatesJsonRequest = RestOf<PartialUpdateCurrentExchangeRatesJsonRequest>;

export type EntityResponseType = HttpResponse<ICurrentExchangeRatesJsonRequest>;
export type EntityArrayResponseType = HttpResponse<ICurrentExchangeRatesJsonRequest[]>;

@Injectable({ providedIn: 'root' })
export class CurrentExchangeRatesJsonRequestService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/current-exchange-rates-json-requests');

  create(currentExchangeRatesJsonRequest: NewCurrentExchangeRatesJsonRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(currentExchangeRatesJsonRequest);
    return this.http
      .post<RestCurrentExchangeRatesJsonRequest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(currentExchangeRatesJsonRequest: ICurrentExchangeRatesJsonRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(currentExchangeRatesJsonRequest);
    return this.http
      .put<RestCurrentExchangeRatesJsonRequest>(
        `${this.resourceUrl}/${this.getCurrentExchangeRatesJsonRequestIdentifier(currentExchangeRatesJsonRequest)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(currentExchangeRatesJsonRequest: PartialUpdateCurrentExchangeRatesJsonRequest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(currentExchangeRatesJsonRequest);
    return this.http
      .patch<RestCurrentExchangeRatesJsonRequest>(
        `${this.resourceUrl}/${this.getCurrentExchangeRatesJsonRequestIdentifier(currentExchangeRatesJsonRequest)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCurrentExchangeRatesJsonRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCurrentExchangeRatesJsonRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCurrentExchangeRatesJsonRequestIdentifier(currentExchangeRatesJsonRequest: Pick<ICurrentExchangeRatesJsonRequest, 'id'>): number {
    return currentExchangeRatesJsonRequest.id;
  }

  compareCurrentExchangeRatesJsonRequest(
    o1: Pick<ICurrentExchangeRatesJsonRequest, 'id'> | null,
    o2: Pick<ICurrentExchangeRatesJsonRequest, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getCurrentExchangeRatesJsonRequestIdentifier(o1) === this.getCurrentExchangeRatesJsonRequestIdentifier(o2)
      : o1 === o2;
  }

  addCurrentExchangeRatesJsonRequestToCollectionIfMissing<Type extends Pick<ICurrentExchangeRatesJsonRequest, 'id'>>(
    currentExchangeRatesJsonRequestCollection: Type[],
    ...currentExchangeRatesJsonRequestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const currentExchangeRatesJsonRequests: Type[] = currentExchangeRatesJsonRequestsToCheck.filter(isPresent);
    if (currentExchangeRatesJsonRequests.length > 0) {
      const currentExchangeRatesJsonRequestCollectionIdentifiers = currentExchangeRatesJsonRequestCollection.map(
        currentExchangeRatesJsonRequestItem => this.getCurrentExchangeRatesJsonRequestIdentifier(currentExchangeRatesJsonRequestItem),
      );
      const currentExchangeRatesJsonRequestsToAdd = currentExchangeRatesJsonRequests.filter(currentExchangeRatesJsonRequestItem => {
        const currentExchangeRatesJsonRequestIdentifier =
          this.getCurrentExchangeRatesJsonRequestIdentifier(currentExchangeRatesJsonRequestItem);
        if (currentExchangeRatesJsonRequestCollectionIdentifiers.includes(currentExchangeRatesJsonRequestIdentifier)) {
          return false;
        }
        currentExchangeRatesJsonRequestCollectionIdentifiers.push(currentExchangeRatesJsonRequestIdentifier);
        return true;
      });
      return [...currentExchangeRatesJsonRequestsToAdd, ...currentExchangeRatesJsonRequestCollection];
    }
    return currentExchangeRatesJsonRequestCollection;
  }

  protected convertDateFromClient<
    T extends ICurrentExchangeRatesJsonRequest | NewCurrentExchangeRatesJsonRequest | PartialUpdateCurrentExchangeRatesJsonRequest,
  >(currentExchangeRatesJsonRequest: T): RestOf<T> {
    return {
      ...currentExchangeRatesJsonRequest,
      timestamp: currentExchangeRatesJsonRequest.timestamp?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restCurrentExchangeRatesJsonRequest: RestCurrentExchangeRatesJsonRequest,
  ): ICurrentExchangeRatesJsonRequest {
    return {
      ...restCurrentExchangeRatesJsonRequest,
      timestamp: restCurrentExchangeRatesJsonRequest.timestamp ? dayjs(restCurrentExchangeRatesJsonRequest.timestamp) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestCurrentExchangeRatesJsonRequest>,
  ): HttpResponse<ICurrentExchangeRatesJsonRequest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestCurrentExchangeRatesJsonRequest[]>,
  ): HttpResponse<ICurrentExchangeRatesJsonRequest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
