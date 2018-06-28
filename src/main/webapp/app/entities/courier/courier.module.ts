import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    CourierComponent,
    CourierDetailComponent,
    CourierUpdateComponent,
    CourierDeletePopupComponent,
    CourierDeleteDialogComponent,
    courierRoute,
    courierPopupRoute
} from './';

const ENTITY_STATES = [...courierRoute, ...courierPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourierComponent,
        CourierDetailComponent,
        CourierUpdateComponent,
        CourierDeleteDialogComponent,
        CourierDeletePopupComponent
    ],
    entryComponents: [CourierComponent, CourierUpdateComponent, CourierDeleteDialogComponent, CourierDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsCourierModule {}
