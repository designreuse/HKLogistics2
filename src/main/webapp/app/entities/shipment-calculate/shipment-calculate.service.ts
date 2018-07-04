import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { FormGroup} from '@angular/forms';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ShipmentCalculateService {
    private resourceUrl = SERVER_API_URL + 'api/courier-pricing-engines/calculateViaPincode?query=';
    
    constructor(private http: HttpClient) {}


    getShipmentCost(f: FormGroup): Observable<any> {
        console.log("form1 -- >>> " , f.value);
        console.log(this.resourceUrl);
        console.log(SERVER_API_URL);
        // Do Rest API Call 
        return this.http.get<any>(this.resourceUrl + f.value, { observe: 'response' );
    }
}
