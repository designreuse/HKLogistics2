import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegionType } from 'app/shared/model/region-type.model';

type EntityResponseType = HttpResponse<IRegionType>;
type EntityArrayResponseType = HttpResponse<IRegionType[]>;

@Injectable({ providedIn: 'root' })
export class RegionTypeService {
    private resourceUrl = SERVER_API_URL + 'api/region-types';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/region-types';

    constructor(private http: HttpClient) {}

    create(regionType: IRegionType): Observable<EntityResponseType> {
        return this.http.post<IRegionType>(this.resourceUrl, regionType, { observe: 'response' });
    }

    update(regionType: IRegionType): Observable<EntityResponseType> {
        return this.http.put<IRegionType>(this.resourceUrl, regionType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRegionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegionType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRegionType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
