import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    PincodeRegionZoneComponent,
    PincodeRegionZoneDetailComponent,
    PincodeRegionZoneUpdateComponent,
    PincodeRegionZoneDeletePopupComponent,
    PincodeRegionZoneDeleteDialogComponent,
    pincodeRegionZoneRoute,
    pincodeRegionZonePopupRoute
} from './';

const ENTITY_STATES = [...pincodeRegionZoneRoute, ...pincodeRegionZonePopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PincodeRegionZoneComponent,
        PincodeRegionZoneDetailComponent,
        PincodeRegionZoneUpdateComponent,
        PincodeRegionZoneDeleteDialogComponent,
        PincodeRegionZoneDeletePopupComponent
    ],
    entryComponents: [
        PincodeRegionZoneComponent,
        PincodeRegionZoneUpdateComponent,
        PincodeRegionZoneDeleteDialogComponent,
        PincodeRegionZoneDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsPincodeRegionZoneModule {}
