import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    AwbStatusComponent,
    AwbStatusDetailComponent,
    AwbStatusUpdateComponent,
    AwbStatusDeletePopupComponent,
    AwbStatusDeleteDialogComponent,
    awbStatusRoute,
    awbStatusPopupRoute
} from './';

const ENTITY_STATES = [...awbStatusRoute, ...awbStatusPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AwbStatusComponent,
        AwbStatusDetailComponent,
        AwbStatusUpdateComponent,
        AwbStatusDeleteDialogComponent,
        AwbStatusDeletePopupComponent
    ],
    entryComponents: [AwbStatusComponent, AwbStatusUpdateComponent, AwbStatusDeleteDialogComponent, AwbStatusDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsAwbStatusModule {}
