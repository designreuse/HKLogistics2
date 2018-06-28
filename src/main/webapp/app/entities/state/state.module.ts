import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    StateComponent,
    StateDetailComponent,
    StateUpdateComponent,
    StateDeletePopupComponent,
    StateDeleteDialogComponent,
    stateRoute,
    statePopupRoute
} from './';

const ENTITY_STATES = [...stateRoute, ...statePopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [StateComponent, StateDetailComponent, StateUpdateComponent, StateDeleteDialogComponent, StateDeletePopupComponent],
    entryComponents: [StateComponent, StateUpdateComponent, StateDeleteDialogComponent, StateDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsStateModule {}
