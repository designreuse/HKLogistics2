import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourier } from 'app/shared/model/courier.model';
import { CourierService } from './courier.service';
import { ICourierGroup } from 'app/shared/model/courier-group.model';
import { CourierGroupService } from 'app/entities/courier-group';

@Component({
    selector: 'jhi-courier-update',
    templateUrl: './courier-update.component.html'
})
export class CourierUpdateComponent implements OnInit {
    private _courier: ICourier;
    isSaving: boolean;

    couriergroups: ICourierGroup[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private courierService: CourierService,
        private courierGroupService: CourierGroupService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courier }) => {
            this.courier = courier;
        });
        this.courierGroupService.query().subscribe(
            (res: HttpResponse<ICourierGroup[]>) => {
                this.couriergroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courier.id !== undefined) {
            this.subscribeToSaveResponse(this.courierService.update(this.courier));
        } else {
            this.subscribeToSaveResponse(this.courierService.create(this.courier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourier>>) {
        result.subscribe((res: HttpResponse<ICourier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCourierGroupById(index: number, item: ICourierGroup) {
        return item.id;
    }
    get courier() {
        return this._courier;
    }

    set courier(courier: ICourier) {
        this._courier = courier;
    }
}
