import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';
import { VendorWHCourierMappingService } from './vendor-wh-courier-mapping.service';
import { ICourier } from 'app/shared/model/courier.model';
import { CourierService } from 'app/entities/courier';

@Component({
    selector: 'jhi-vendor-wh-courier-mapping-update',
    templateUrl: './vendor-wh-courier-mapping-update.component.html'
})
export class VendorWHCourierMappingUpdateComponent implements OnInit {
    private _vendorWHCourierMapping: IVendorWHCourierMapping;
    isSaving: boolean;

    couriers: ICourier[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private vendorWHCourierMappingService: VendorWHCourierMappingService,
        private courierService: CourierService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vendorWHCourierMapping }) => {
            this.vendorWHCourierMapping = vendorWHCourierMapping;
        });
        this.courierService.query().subscribe(
            (res: HttpResponse<ICourier[]>) => {
                this.couriers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.vendorWHCourierMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.vendorWHCourierMappingService.update(this.vendorWHCourierMapping));
        } else {
            this.subscribeToSaveResponse(this.vendorWHCourierMappingService.create(this.vendorWHCourierMapping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVendorWHCourierMapping>>) {
        result.subscribe(
            (res: HttpResponse<IVendorWHCourierMapping>) => this.onSaveSuccess(),
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
    get vendorWHCourierMapping() {
        return this._vendorWHCourierMapping;
    }

    set vendorWHCourierMapping(vendorWHCourierMapping: IVendorWHCourierMapping) {
        this._vendorWHCourierMapping = vendorWHCourierMapping;
    }
}
