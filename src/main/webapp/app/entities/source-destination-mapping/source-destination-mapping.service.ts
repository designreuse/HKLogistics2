import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';

type EntityResponseType = HttpResponse<ISourceDestinationMapping>;
type EntityArrayResponseType = HttpResponse<ISourceDestinationMapping[]>;

@Injectable({ providedIn: 'root' })
export class SourceDestinationMappingService {
    private resourceUrl = SERVER_API_URL + 'api/source-destination-mappings';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/source-destination-mappings';

    constructor(private http: HttpClient) {}

    create(sourceDestinationMapping: ISourceDestinationMapping): Observable<EntityResponseType> {
        return this.http.post<ISourceDestinationMapping>(this.resourceUrl, sourceDestinationMapping, { observe: 'response' });
    }

    update(sourceDestinationMapping: ISourceDestinationMapping): Observable<EntityResponseType> {
        return this.http.put<ISourceDestinationMapping>(this.resourceUrl, sourceDestinationMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISourceDestinationMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISourceDestinationMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISourceDestinationMapping[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
