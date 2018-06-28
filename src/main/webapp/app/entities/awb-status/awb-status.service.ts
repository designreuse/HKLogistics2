import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAwbStatus } from 'app/shared/model/awb-status.model';

type EntityResponseType = HttpResponse<IAwbStatus>;
type EntityArrayResponseType = HttpResponse<IAwbStatus[]>;

@Injectable({ providedIn: 'root' })
export class AwbStatusService {
    private resourceUrl = SERVER_API_URL + 'api/awb-statuses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/awb-statuses';

    constructor(private http: HttpClient) {}

    create(awbStatus: IAwbStatus): Observable<EntityResponseType> {
        return this.http.post<IAwbStatus>(this.resourceUrl, awbStatus, { observe: 'response' });
    }

    update(awbStatus: IAwbStatus): Observable<EntityResponseType> {
        return this.http.put<IAwbStatus>(this.resourceUrl, awbStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAwbStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAwbStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAwbStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
