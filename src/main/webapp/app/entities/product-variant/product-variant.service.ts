import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductVariant } from 'app/shared/model/product-variant.model';

type EntityResponseType = HttpResponse<IProductVariant>;
type EntityArrayResponseType = HttpResponse<IProductVariant[]>;

@Injectable({ providedIn: 'root' })
export class ProductVariantService {
    private resourceUrl = SERVER_API_URL + 'api/product-variants';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/product-variants';

    constructor(private http: HttpClient) {}

    create(productVariant: IProductVariant): Observable<EntityResponseType> {
        return this.http.post<IProductVariant>(this.resourceUrl, productVariant, { observe: 'response' });
    }

    update(productVariant: IProductVariant): Observable<EntityResponseType> {
        return this.http.put<IProductVariant>(this.resourceUrl, productVariant, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductVariant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductVariant[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductVariant[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
