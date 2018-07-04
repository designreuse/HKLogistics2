import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';

type EntityResponseType = HttpResponse<ICourierPricingEngine>;
type EntityArrayResponseType = HttpResponse<ICourierPricingEngine[]>;

@Injectable({ providedIn: 'root' })
export class CourierPricingEngineService {
    private resourceUrl = SERVER_API_URL + 'api/courier-pricing-engines';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/courier-pricing-engines';

    constructor(private http: HttpClient) {}

    create(courierPricingEngine: ICourierPricingEngine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(courierPricingEngine);
        return this.http
            .post<ICourierPricingEngine>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(courierPricingEngine: ICourierPricingEngine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(courierPricingEngine);
        return this.http
            .put<ICourierPricingEngine>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICourierPricingEngine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICourierPricingEngine[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICourierPricingEngine[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    private convertDateFromClient(courierPricingEngine: ICourierPricingEngine): ICourierPricingEngine {
        const copy: ICourierPricingEngine = Object.assign({}, courierPricingEngine, {
            validUpto:
                courierPricingEngine.validUpto != null && courierPricingEngine.validUpto.isValid()
                    ? courierPricingEngine.validUpto.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.validUpto = res.body.validUpto != null ? moment(res.body.validUpto) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((courierPricingEngine: ICourierPricingEngine) => {
            courierPricingEngine.validUpto = courierPricingEngine.validUpto != null ? moment(courierPricingEngine.validUpto) : null;
        });
        return res;
    }

    filter(courierId:number): Observable<EntityArrayResponseType> {
        console.log('req.filter');
        // const options = createRequestOption(req);
        let filterUrl = this.resourceUrl + '/filter?';
        if(courierId)
        {
            filterUrl = filterUrl + 'courierId.equals=' + courierId; 
        }
        return this.http.get<ICourierPricingEngine[]>( filterUrl, { observe: 'response' });
    }
}
