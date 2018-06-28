import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IShipmentServiceType } from 'app/shared/model/shipment-service-type.model';
import { ShipmentServiceTypeService } from './shipment-service-type.service';

@Component({
    selector: 'jhi-shipment-service-type-update',
    templateUrl: './shipment-service-type-update.component.html'
})
export class ShipmentServiceTypeUpdateComponent implements OnInit {
    private _shipmentServiceType: IShipmentServiceType;
    isSaving: boolean;

    constructor(private shipmentServiceTypeService: ShipmentServiceTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ shipmentServiceType }) => {
            this.shipmentServiceType = shipmentServiceType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.shipmentServiceType.id !== undefined) {
            this.subscribeToSaveResponse(this.shipmentServiceTypeService.update(this.shipmentServiceType));
        } else {
            this.subscribeToSaveResponse(this.shipmentServiceTypeService.create(this.shipmentServiceType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IShipmentServiceType>>) {
        result.subscribe((res: HttpResponse<IShipmentServiceType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get shipmentServiceType() {
        return this._shipmentServiceType;
    }

    set shipmentServiceType(shipmentServiceType: IShipmentServiceType) {
        this._shipmentServiceType = shipmentServiceType;
    }
}
