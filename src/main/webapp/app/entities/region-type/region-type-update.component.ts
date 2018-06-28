import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRegionType } from 'app/shared/model/region-type.model';
import { RegionTypeService } from './region-type.service';

@Component({
    selector: 'jhi-region-type-update',
    templateUrl: './region-type-update.component.html'
})
export class RegionTypeUpdateComponent implements OnInit {
    private _regionType: IRegionType;
    isSaving: boolean;

    constructor(private regionTypeService: RegionTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ regionType }) => {
            this.regionType = regionType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.regionType.id !== undefined) {
            this.subscribeToSaveResponse(this.regionTypeService.update(this.regionType));
        } else {
            this.subscribeToSaveResponse(this.regionTypeService.create(this.regionType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRegionType>>) {
        result.subscribe((res: HttpResponse<IRegionType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get regionType() {
        return this._regionType;
    }

    set regionType(regionType: IRegionType) {
        this._regionType = regionType;
    }
}
