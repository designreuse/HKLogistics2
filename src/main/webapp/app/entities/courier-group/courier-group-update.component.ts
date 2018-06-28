import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICourierGroup } from 'app/shared/model/courier-group.model';
import { CourierGroupService } from './courier-group.service';

@Component({
    selector: 'jhi-courier-group-update',
    templateUrl: './courier-group-update.component.html'
})
export class CourierGroupUpdateComponent implements OnInit {
    private _courierGroup: ICourierGroup;
    isSaving: boolean;

    constructor(private courierGroupService: CourierGroupService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courierGroup }) => {
            this.courierGroup = courierGroup;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courierGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.courierGroupService.update(this.courierGroup));
        } else {
            this.subscribeToSaveResponse(this.courierGroupService.create(this.courierGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourierGroup>>) {
        result.subscribe((res: HttpResponse<ICourierGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get courierGroup() {
        return this._courierGroup;
    }

    set courierGroup(courierGroup: ICourierGroup) {
        this._courierGroup = courierGroup;
    }
}
