import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourier } from 'app/shared/model/courier.model';

type EntityResponseType = HttpResponse<ICourier>;
type EntityArrayResponseType = HttpResponse<ICourier[]>;

@Injectable({ providedIn: 'root' })
export class CourierService {
    private resourceUrl = SERVER_API_URL + 'api/couriers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/couriers';

    constructor(private http: HttpClient) {}

    create(courier: ICourier): Observable<EntityResponseType> {
        return this.http.post<ICourier>(this.resourceUrl, courier, { observe: 'response' });
    }

    update(courier: ICourier): Observable<EntityResponseType> {
        return this.http.put<ICourier>(this.resourceUrl, courier, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourier[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourier[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
