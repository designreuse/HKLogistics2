import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    CourierGroupComponent,
    CourierGroupDetailComponent,
    CourierGroupUpdateComponent,
    CourierGroupDeletePopupComponent,
    CourierGroupDeleteDialogComponent,
    courierGroupRoute,
    courierGroupPopupRoute
} from './';

const ENTITY_STATES = [...courierGroupRoute, ...courierGroupPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourierGroupComponent,
        CourierGroupDetailComponent,
        CourierGroupUpdateComponent,
        CourierGroupDeleteDialogComponent,
        CourierGroupDeletePopupComponent
    ],
    entryComponents: [
        CourierGroupComponent,
        CourierGroupUpdateComponent,
        CourierGroupDeleteDialogComponent,
        CourierGroupDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsCourierGroupModule {}
