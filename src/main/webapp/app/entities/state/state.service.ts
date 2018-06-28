import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IState } from 'app/shared/model/state.model';

type EntityResponseType = HttpResponse<IState>;
type EntityArrayResponseType = HttpResponse<IState[]>;

@Injectable({ providedIn: 'root' })
export class StateService {
    private resourceUrl = SERVER_API_URL + 'api/states';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/states';

    constructor(private http: HttpClient) {}

    create(state: IState): Observable<EntityResponseType> {
        return this.http.post<IState>(this.resourceUrl, state, { observe: 'response' });
    }

    update(state: IState): Observable<EntityResponseType> {
        return this.http.put<IState>(this.resourceUrl, state, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IState>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IState[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IState[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
