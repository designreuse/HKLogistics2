import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    ShipmentServiceTypeComponent,
    ShipmentServiceTypeDetailComponent,
    ShipmentServiceTypeUpdateComponent,
    ShipmentServiceTypeDeletePopupComponent,
    ShipmentServiceTypeDeleteDialogComponent,
    shipmentServiceTypeRoute,
    shipmentServiceTypePopupRoute
} from './';

const ENTITY_STATES = [...shipmentServiceTypeRoute, ...shipmentServiceTypePopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ShipmentServiceTypeComponent,
        ShipmentServiceTypeDetailComponent,
        ShipmentServiceTypeUpdateComponent,
        ShipmentServiceTypeDeleteDialogComponent,
        ShipmentServiceTypeDeletePopupComponent
    ],
    entryComponents: [
        ShipmentServiceTypeComponent,
        ShipmentServiceTypeUpdateComponent,
        ShipmentServiceTypeDeleteDialogComponent,
        ShipmentServiceTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsShipmentServiceTypeModule {}
