import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';

type EntityResponseType = HttpResponse<IVendorWHCourierMapping>;
type EntityArrayResponseType = HttpResponse<IVendorWHCourierMapping[]>;

@Injectable({ providedIn: 'root' })
export class VendorWHCourierMappingService {
    private resourceUrl = SERVER_API_URL + 'api/vendor-wh-courier-mappings';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/vendor-wh-courier-mappings';

    constructor(private http: HttpClient) {}

    create(vendorWHCourierMapping: IVendorWHCourierMapping): Observable<EntityResponseType> {
        return this.http.post<IVendorWHCourierMapping>(this.resourceUrl, vendorWHCourierMapping, { observe: 'response' });
    }

    update(vendorWHCourierMapping: IVendorWHCourierMapping): Observable<EntityResponseType> {
        return this.http.put<IVendorWHCourierMapping>(this.resourceUrl, vendorWHCourierMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVendorWHCourierMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVendorWHCourierMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVendorWHCourierMapping[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
