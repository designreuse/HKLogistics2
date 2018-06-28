import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShipmentServiceType } from 'app/shared/model/shipment-service-type.model';

type EntityResponseType = HttpResponse<IShipmentServiceType>;
type EntityArrayResponseType = HttpResponse<IShipmentServiceType[]>;

@Injectable({ providedIn: 'root' })
export class ShipmentServiceTypeService {
    private resourceUrl = SERVER_API_URL + 'api/shipment-service-types';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/shipment-service-types';

    constructor(private http: HttpClient) {}

    create(shipmentServiceType: IShipmentServiceType): Observable<EntityResponseType> {
        return this.http.post<IShipmentServiceType>(this.resourceUrl, shipmentServiceType, { observe: 'response' });
    }

    update(shipmentServiceType: IShipmentServiceType): Observable<EntityResponseType> {
        return this.http.put<IShipmentServiceType>(this.resourceUrl, shipmentServiceType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IShipmentServiceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShipmentServiceType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShipmentServiceType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
