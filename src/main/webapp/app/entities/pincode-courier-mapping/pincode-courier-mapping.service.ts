import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';

type EntityResponseType = HttpResponse<IPincodeCourierMapping>;
type EntityArrayResponseType = HttpResponse<IPincodeCourierMapping[]>;

@Injectable({ providedIn: 'root' })
export class PincodeCourierMappingService {
    private resourceUrl = SERVER_API_URL + 'api/pincode-courier-mappings';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/pincode-courier-mappings';

    constructor(private http: HttpClient) {}

    create(pincodeCourierMapping: IPincodeCourierMapping): Observable<EntityResponseType> {
        return this.http.post<IPincodeCourierMapping>(this.resourceUrl, pincodeCourierMapping, { observe: 'response' });
    }

    update(pincodeCourierMapping: IPincodeCourierMapping): Observable<EntityResponseType> {
        return this.http.put<IPincodeCourierMapping>(this.resourceUrl, pincodeCourierMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPincodeCourierMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincodeCourierMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincodeCourierMapping[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
