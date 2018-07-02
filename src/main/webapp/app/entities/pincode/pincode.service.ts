import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPincode } from 'app/shared/model/pincode.model';

type EntityResponseType = HttpResponse<IPincode>;
type EntityArrayResponseType = HttpResponse<IPincode[]>;

@Injectable({ providedIn: 'root' })
export class PincodeService {
    private resourceUrl = SERVER_API_URL + 'api/pincodes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/pincodes';

    constructor(private http: HttpClient) {}

    create(pincode: IPincode): Observable<EntityResponseType> {
        return this.http.post<IPincode>(this.resourceUrl, pincode, { observe: 'response' });
    }

    update(pincode: IPincode): Observable<EntityResponseType> {
        return this.http.put<IPincode>(this.resourceUrl, pincode, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPincode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    searchName(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPincode[]>(this.resourceSearchUrl + '/name', { params: options, observe: 'response' });
    }

    downloadFile() {
        const finalUrl = this.resourceUrl + '/download';
        const fileType = '.xls';
        const filename = 'pincodes' + fileType;
        console.log('downloadFile Service Called ' + finalUrl);
        return this.http
            .get(finalUrl, {
                responseType: 'blob'
            })
            .map(res => {
                return {
                    filename: filename,
                    data: res
                };
            })
            .subscribe(res => {
                const url = window.URL.createObjectURL(res.data);
                const a = document.createElement('a');
                document.body.appendChild(a);
                a.setAttribute('style', 'display: none');
                a.href = url;
                a.download = res.filename;
                a.click();
                window.URL.revokeObjectURL(url);
                a.remove(); // remove the element
            }, error => {
                console.log(error);
                alert(error.message);
            }, () => {
                console.log('Completed file download.');
            });
    }

    public uploadFile(fileToUpload: File) {
        const _formData = new FormData();
        _formData.append('file', fileToUpload, fileToUpload.name);
        return this.http.post<IPincode>(this.resourceUrl + '/upload', _formData, { observe: 'response' });
    }
}
