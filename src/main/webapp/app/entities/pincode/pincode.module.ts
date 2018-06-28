import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    PincodeComponent,
    PincodeDetailComponent,
    PincodeUpdateComponent,
    PincodeDeletePopupComponent,
    PincodeDeleteDialogComponent,
    pincodeRoute,
    pincodePopupRoute
} from './';

const ENTITY_STATES = [...pincodeRoute, ...pincodePopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PincodeComponent,
        PincodeDetailComponent,
        PincodeUpdateComponent,
        PincodeDeleteDialogComponent,
        PincodeDeletePopupComponent
    ],
    entryComponents: [PincodeComponent, PincodeUpdateComponent, PincodeDeleteDialogComponent, PincodeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsPincodeModule {}
