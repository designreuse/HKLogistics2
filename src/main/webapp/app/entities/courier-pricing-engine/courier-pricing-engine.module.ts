import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    CourierPricingEngineComponent,
    CourierPricingEngineDetailComponent,
    CourierPricingEngineUpdateComponent,
    CourierPricingEngineDeletePopupComponent,
    CourierPricingEngineDeleteDialogComponent,
    courierPricingEngineRoute,
    courierPricingEnginePopupRoute
} from './';

const ENTITY_STATES = [...courierPricingEngineRoute, ...courierPricingEnginePopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourierPricingEngineComponent,
        CourierPricingEngineDetailComponent,
        CourierPricingEngineUpdateComponent,
        CourierPricingEngineDeleteDialogComponent,
        CourierPricingEngineDeletePopupComponent
    ],
    entryComponents: [
        CourierPricingEngineComponent,
        CourierPricingEngineUpdateComponent,
        CourierPricingEngineDeleteDialogComponent,
        CourierPricingEngineDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsCourierPricingEngineModule {}
