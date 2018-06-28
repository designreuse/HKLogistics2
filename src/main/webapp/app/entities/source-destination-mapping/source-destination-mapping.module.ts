import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    SourceDestinationMappingComponent,
    SourceDestinationMappingDetailComponent,
    SourceDestinationMappingUpdateComponent,
    SourceDestinationMappingDeletePopupComponent,
    SourceDestinationMappingDeleteDialogComponent,
    sourceDestinationMappingRoute,
    sourceDestinationMappingPopupRoute
} from './';

const ENTITY_STATES = [...sourceDestinationMappingRoute, ...sourceDestinationMappingPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SourceDestinationMappingComponent,
        SourceDestinationMappingDetailComponent,
        SourceDestinationMappingUpdateComponent,
        SourceDestinationMappingDeleteDialogComponent,
        SourceDestinationMappingDeletePopupComponent
    ],
    entryComponents: [
        SourceDestinationMappingComponent,
        SourceDestinationMappingUpdateComponent,
        SourceDestinationMappingDeleteDialogComponent,
        SourceDestinationMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsSourceDestinationMappingModule {}
