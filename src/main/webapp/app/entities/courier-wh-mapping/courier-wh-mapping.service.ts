import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { CourierWHMapping } from './courier-wh-mapping.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CourierWHMappingService {
    private resourceUrl = SERVER_API_URL + 'api/wh-courier-mapping';
    
    constructor(private http: HttpClient) {}


    createNew(model: CourierWHMapping): Observable<any> {
        console.log("form1 -- >>> " , model)
        // Do Rest API Call 
        // return this.http
        //     .post<IAwb>(this.resourceUrl, form1, { observe: 'response' })
        //     .map((res: any) => res);
        return null;
    }
}
