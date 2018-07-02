import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAwb } from 'app/shared/model/awb.model';
import { TagContentType } from '@angular/compiler';

import { ICourier } from 'app/shared/model/courier.model';
import { IAwbStatus } from 'app/shared/model/awb-status.model';

type EntityResponseType = HttpResponse<IAwb>;
type EntityArrayResponseType = HttpResponse<IAwb[]>;

@Injectable({ providedIn: 'root' })
export class AwbService {
    private resourceUrl = SERVER_API_URL + 'api/awbs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/awbs';
    private downloadUrl = SERVER_API_URL + 'api/awbs/download';

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

    downloadFile(courier: ICourier, awbStatus: IAwbStatus) {
        let finalUrl = this.downloadUrl ;
        if ( courier.id ) {
            finalUrl = finalUrl + '/?courierId.equals=' + courier.id ;
        }
        if ( awbStatus.id ) {
            finalUrl = finalUrl + '&awbStatusId.equals=' + awbStatus.id;
        }
        const fileType = '.xls';
        const filename = 'courier-awb-status' + fileType;
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

    filter(req?: any): Observable<HttpResponse<any> {
        console.log('req.filter');
        const options = createRequestOption(req);
        return this.http.get<any>(this.resourceUrl + '/download' , { params: options, observe: 'response' });
    }

    public uploadFile(fileToUpload: File) {
        const _formData = new FormData();
        _formData.append('file', fileToUpload, fileToUpload.name);
        return this.http.post<IAwb>(this.resourceUrl + '/upload', _formData, { observe: 'response' });
    }

    public deleteBulk(fileToUpload: File) {
        const _formData = new FormData();
        _formData.append('file', fileToUpload, fileToUpload.name);
        return this.http.post<IAwb>(this.resourceUrl + '/awbs/bulk-delete', _formData, { observe: 'response' });
    }
}
