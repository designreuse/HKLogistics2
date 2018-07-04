import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';
import { ICourierGroup } from 'app/shared/model/courier-group.model';

type EntityResponseType = HttpResponse<IPincodeRegionZone>;
type EntityArrayResponseType = HttpResponse<IPincodeRegionZone[]>;

@Injectable({ providedIn: 'root' })
export class PincodeRegionZoneService {
    private resourceUrl = SERVER_API_URL + 'api/pincode-region-zones';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/pincode-region-zones';

    constructor(private http: HttpClient) {}

    create(pincodeRegionZone: IPincodeRegionZone): Observable<EntityResponseType> {
        return this.http.post<IPincodeRegionZone>(this.resourceUrl, pincodeRegionZone, { observe: 'response' });
    }

    update(pincodeRegionZone: IPincodeRegionZone): Observable<EntityResponseType> {
        return this.http.put<IPincodeRegionZone>(this.resourceUrl, pincodeRegionZone, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPincodeRegionZone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincodeRegionZone[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincodeRegionZone[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }

    filter(sourcePincode: string, destinationPincode: string, courierGroup: ICourierGroup, 
        regionTypeId: number): Observable<EntityArrayResponseType> {
        console.log('req.filter');
        // const options = createRequestOption(req);
        let filterUrl = this.resourceUrl + '/filter?';
        if(destinationPincode)
        {
            filterUrl = filterUrl + 'destinationPincode.equals=' + destinationPincode;
        }
        if(sourcePincode)
        {
            filterUrl = filterUrl + '&sourcePincode.equals=' + sourcePincode;
        }
        if(courierGroup)
        {
            filterUrl = filterUrl + '&courierGroupId.equals=' + courierGroup.id;
        }
        if(regionTypeId)
        {
            filterUrl = filterUrl + '&regionTypeId.equals=' + regionTypeId;
        }
        return this.http.get<IPincodeRegionZone[]>( filterUrl, { observe: 'response' });
    }

    public uploadFile(fileToUpload: File) {
        const _formData = new FormData();
        _formData.append('file', fileToUpload, fileToUpload.name);
        return this.http.post<IPincodeRegionZone>(this.resourceUrl + '/upload', _formData, { observe: 'response' });
    }
}
