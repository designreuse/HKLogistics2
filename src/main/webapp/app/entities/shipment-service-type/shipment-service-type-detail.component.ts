import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShipmentServiceType } from 'app/shared/model/shipment-service-type.model';

@Component({
    selector: 'jhi-shipment-service-type-detail',
    templateUrl: './shipment-service-type-detail.component.html'
})
export class ShipmentServiceTypeDetailComponent implements OnInit {
    shipmentServiceType: IShipmentServiceType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shipmentServiceType }) => {
            this.shipmentServiceType = shipmentServiceType;
        });
    }

    previousState() {
        window.history.back();
    }
}
