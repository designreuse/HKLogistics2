import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAwb } from 'app/shared/model/awb.model';

type EntityResponseType = HttpResponse<IAwb>;
type EntityArrayResponseType = HttpResponse<IAwb[]>;

@Injectable({ providedIn: 'root' })
export class AwbService {
    private resourceUrl = SERVER_API_URL + 'api/awbs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/awbs';

    constructor(private http: HttpClient) {}

    create(awb: IAwb): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(awb);
        return this.http
            .post<IAwb>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(awb: IAwb): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(awb);
        return this.http
            .put<IAwb>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAwb>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAwb[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAwb[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(awb: IAwb): IAwb {
        const copy: IAwb = Object.assign({}, awb, {
            createDate: awb.createDate != null && awb.createDate.isValid() ? awb.createDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.createDate = res.body.createDate != null ? moment(res.body.createDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((awb: IAwb) => {
            awb.createDate = awb.createDate != null ? moment(awb.createDate) : null;
        });
        return res;
    }
}
