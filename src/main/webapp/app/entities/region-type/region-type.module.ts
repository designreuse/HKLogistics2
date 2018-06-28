import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    RegionTypeComponent,
    RegionTypeDetailComponent,
    RegionTypeUpdateComponent,
    RegionTypeDeletePopupComponent,
    RegionTypeDeleteDialogComponent,
    regionTypeRoute,
    regionTypePopupRoute
} from './';

const ENTITY_STATES = [...regionTypeRoute, ...regionTypePopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RegionTypeComponent,
        RegionTypeDetailComponent,
        RegionTypeUpdateComponent,
        RegionTypeDeleteDialogComponent,
        RegionTypeDeletePopupComponent
    ],
    entryComponents: [RegionTypeComponent, RegionTypeUpdateComponent, RegionTypeDeleteDialogComponent, RegionTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsRegionTypeModule {}
