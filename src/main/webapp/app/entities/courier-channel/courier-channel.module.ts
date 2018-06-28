import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    CourierChannelComponent,
    CourierChannelDetailComponent,
    CourierChannelUpdateComponent,
    CourierChannelDeletePopupComponent,
    CourierChannelDeleteDialogComponent,
    courierChannelRoute,
    courierChannelPopupRoute
} from './';

const ENTITY_STATES = [...courierChannelRoute, ...courierChannelPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourierChannelComponent,
        CourierChannelDetailComponent,
        CourierChannelUpdateComponent,
        CourierChannelDeleteDialogComponent,
        CourierChannelDeletePopupComponent
    ],
    entryComponents: [
        CourierChannelComponent,
        CourierChannelUpdateComponent,
        CourierChannelDeleteDialogComponent,
        CourierChannelDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsCourierChannelModule {}
