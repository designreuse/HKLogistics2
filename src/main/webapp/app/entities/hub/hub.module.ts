import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    HubComponent,
    HubDetailComponent,
    HubUpdateComponent,
    HubDeletePopupComponent,
    HubDeleteDialogComponent,
    hubRoute,
    hubPopupRoute
} from './';

const ENTITY_STATES = [...hubRoute, ...hubPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [HubComponent, HubDetailComponent, HubUpdateComponent, HubDeleteDialogComponent, HubDeletePopupComponent],
    entryComponents: [HubComponent, HubUpdateComponent, HubDeleteDialogComponent, HubDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsHubModule {}
