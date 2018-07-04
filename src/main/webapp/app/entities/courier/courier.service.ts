import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourier } from 'app/shared/model/courier.model';
import { ICourierGroup } from 'app/shared/model/courier-group.model';

type EntityResponseType = HttpResponse<ICourier>;
type EntityArrayResponseType = HttpResponse<ICourier[]>;

@Injectable({ providedIn: 'root' })
export class CourierService {
    private resourceUrl = SERVER_API_URL + 'api/couriers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/couriers';

    constructor(private http: HttpClient) {}

    create(courier: ICourier): Observable<EntityResponseType> {
        return this.http.post<ICourier>(this.resourceUrl, courier, { observe: 'response' });
    }

    update(courier: ICourier): Observable<EntityResponseType> {
        return this.http.put<ICourier>(this.resourceUrl, courier, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourier[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    searchName(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourier[]>(this.resourceSearchUrl + '/name' , { params: options, observe: 'response' });
    }

    filter(courierGroup: ICourierGroup, currentSearchName: String, status:String, operation: String, params:any): Observable<EntityArrayResponseType> {
        console.log('req.filter');
        const options = createRequestOption(params);
        // const options = createRequestOption(req);
        let filterUrl = this.resourceUrl + '/filter?';
        if(courierGroup)
        {
            filterUrl = filterUrl + 'courierGroupId.equals=' + courierGroup.id;
        }
        if(currentSearchName)
        {
            filterUrl = filterUrl + '&name.equals=' + currentSearchName;
        }
        if(status)
        {
            filterUrl = filterUrl + '&active.equals=' + status;
        }
        if(operation)
        {
            filterUrl = filterUrl + '&' + operation + '.equals=true';
        }
        return this.http.get<ICourier[]>( filterUrl, {  params: options, observe: 'response' });
    }
}
