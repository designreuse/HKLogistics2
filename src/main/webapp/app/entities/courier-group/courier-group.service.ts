import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourierGroup } from 'app/shared/model/courier-group.model';

type EntityResponseType = HttpResponse<ICourierGroup>;
type EntityArrayResponseType = HttpResponse<ICourierGroup[]>;

@Injectable({ providedIn: 'root' })
export class CourierGroupService {
    private resourceUrl = SERVER_API_URL + 'api/courier-groups';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/courier-groups';

    constructor(private http: HttpClient) {}

    create(courierGroup: ICourierGroup): Observable<EntityResponseType> {
        return this.http.post<ICourierGroup>(this.resourceUrl, courierGroup, { observe: 'response' });
    }

    update(courierGroup: ICourierGroup): Observable<EntityResponseType> {
        return this.http.put<ICourierGroup>(this.resourceUrl, courierGroup, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourierGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourierGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourierGroup[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
