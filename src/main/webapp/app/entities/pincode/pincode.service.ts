import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPincode } from 'app/shared/model/pincode.model';

type EntityResponseType = HttpResponse<IPincode>;
type EntityArrayResponseType = HttpResponse<IPincode[]>;

@Injectable({ providedIn: 'root' })
export class PincodeService {
    private resourceUrl = SERVER_API_URL + 'api/pincodes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/pincodes';

    constructor(private http: HttpClient) {}

    create(pincode: IPincode): Observable<EntityResponseType> {
        return this.http.post<IPincode>(this.resourceUrl, pincode, { observe: 'response' });
    }

    update(pincode: IPincode): Observable<EntityResponseType> {
        return this.http.put<IPincode>(this.resourceUrl, pincode, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPincode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    searchName(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincode[]>(this.resourceSearchUrl + '/name', { params: options, observe: 'response' });
    }
}
