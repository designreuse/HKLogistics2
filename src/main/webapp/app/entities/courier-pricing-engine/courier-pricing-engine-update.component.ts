import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';
import { CourierPricingEngineService } from './courier-pricing-engine.service';
import { ICourier } from 'app/shared/model/courier.model';
import { CourierService } from 'app/entities/courier';
import { IRegionType } from 'app/shared/model/region-type.model';
import { RegionTypeService } from 'app/entities/region-type';

@Component({
    selector: 'jhi-courier-pricing-engine-update',
    templateUrl: './courier-pricing-engine-update.component.html'
})
export class CourierPricingEngineUpdateComponent implements OnInit {
    private _courierPricingEngine: ICourierPricingEngine;
    isSaving: boolean;

    couriers: ICourier[];

    regiontypes: IRegionType[];
    validUptoDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private courierPricingEngineService: CourierPricingEngineService,
        private courierService: CourierService,
        private regionTypeService: RegionTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courierPricingEngine }) => {
            this.courierPricingEngine = courierPricingEngine;
        });
        this.courierService.query().subscribe(
            (res: HttpResponse<ICourier[]>) => {
                this.couriers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.regionTypeService.query().subscribe(
            (res: HttpResponse<IRegionType[]>) => {
                this.regiontypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courierPricingEngine.id !== undefined) {
            this.subscribeToSaveResponse(this.courierPricingEngineService.update(this.courierPricingEngine));
        } else {
            this.subscribeToSaveResponse(this.courierPricingEngineService.create(this.courierPricingEngine));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourierPricingEngine>>) {
        result.subscribe(
            (res: HttpResponse<ICourierPricingEngine>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackCourierById(index: number, item: ICourier) {
        return item.id;
    }

    trackRegionTypeById(index: number, item: IRegionType) {
        return item.id;
    }
    get courierPricingEngine() {
        return this._courierPricingEngine;
    }

    set courierPricingEngine(courierPricingEngine: ICourierPricingEngine) {
        this._courierPricingEngine = courierPricingEngine;
    }
}
