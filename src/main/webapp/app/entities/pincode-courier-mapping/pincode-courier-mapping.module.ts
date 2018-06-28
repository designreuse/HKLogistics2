import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    PincodeCourierMappingComponent,
    PincodeCourierMappingDetailComponent,
    PincodeCourierMappingUpdateComponent,
    PincodeCourierMappingDeletePopupComponent,
    PincodeCourierMappingDeleteDialogComponent,
    pincodeCourierMappingRoute,
    pincodeCourierMappingPopupRoute
} from './';

const ENTITY_STATES = [...pincodeCourierMappingRoute, ...pincodeCourierMappingPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PincodeCourierMappingComponent,
        PincodeCourierMappingDetailComponent,
        PincodeCourierMappingUpdateComponent,
        PincodeCourierMappingDeleteDialogComponent,
        PincodeCourierMappingDeletePopupComponent
    ],
    entryComponents: [
        PincodeCourierMappingComponent,
        PincodeCourierMappingUpdateComponent,
        PincodeCourierMappingDeleteDialogComponent,
        PincodeCourierMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsPincodeCourierMappingModule {}
