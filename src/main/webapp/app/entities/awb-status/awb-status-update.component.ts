import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAwbStatus } from 'app/shared/model/awb-status.model';
import { AwbStatusService } from './awb-status.service';

@Component({
    selector: 'jhi-awb-status-update',
    templateUrl: './awb-status-update.component.html'
})
export class AwbStatusUpdateComponent implements OnInit {
    private _awbStatus: IAwbStatus;
    isSaving: boolean;

    constructor(private awbStatusService: AwbStatusService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ awbStatus }) => {
            this.awbStatus = awbStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.awbStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.awbStatusService.update(this.awbStatus));
        } else {
            this.subscribeToSaveResponse(this.awbStatusService.create(this.awbStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAwbStatus>>) {
        result.subscribe((res: HttpResponse<IAwbStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get awbStatus() {
        return this._awbStatus;
    }

    set awbStatus(awbStatus: IAwbStatus) {
        this._awbStatus = awbStatus;
    }
}
