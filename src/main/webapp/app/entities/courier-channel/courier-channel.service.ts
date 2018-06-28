import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourierChannel } from 'app/shared/model/courier-channel.model';

type EntityResponseType = HttpResponse<ICourierChannel>;
type EntityArrayResponseType = HttpResponse<ICourierChannel[]>;

@Injectable({ providedIn: 'root' })
export class CourierChannelService {
    private resourceUrl = SERVER_API_URL + 'api/courier-channels';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/courier-channels';

    constructor(private http: HttpClient) {}

    create(courierChannel: ICourierChannel): Observable<EntityResponseType> {
        return this.http.post<ICourierChannel>(this.resourceUrl, courierChannel, { observe: 'response' });
    }

    update(courierChannel: ICourierChannel): Observable<EntityResponseType> {
        return this.http.put<ICourierChannel>(this.resourceUrl, courierChannel, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourierChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourierChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourierChannel[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
