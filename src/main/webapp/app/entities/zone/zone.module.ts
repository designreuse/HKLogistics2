import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    ZoneComponent,
    ZoneDetailComponent,
    ZoneUpdateComponent,
    ZoneDeletePopupComponent,
    ZoneDeleteDialogComponent,
    zoneRoute,
    zonePopupRoute
} from './';

const ENTITY_STATES = [...zoneRoute, ...zonePopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ZoneComponent, ZoneDetailComponent, ZoneUpdateComponent, ZoneDeleteDialogComponent, ZoneDeletePopupComponent],
    entryComponents: [ZoneComponent, ZoneUpdateComponent, ZoneDeleteDialogComponent, ZoneDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsZoneModule {}
