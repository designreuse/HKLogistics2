import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    AwbComponent,
    AwbDetailComponent,
    AwbUpdateComponent,
    AwbDeletePopupComponent,
    AwbDeleteDialogComponent,
    awbRoute,
    awbPopupRoute
} from './';

const ENTITY_STATES = [...awbRoute, ...awbPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AwbComponent, AwbDetailComponent, AwbUpdateComponent, AwbDeleteDialogComponent, AwbDeletePopupComponent],
    entryComponents: [AwbComponent, AwbUpdateComponent, AwbDeleteDialogComponent, AwbDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsAwbModule {}
