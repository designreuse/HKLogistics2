import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    VendorWHCourierMappingComponent,
    VendorWHCourierMappingDetailComponent,
    VendorWHCourierMappingUpdateComponent,
    VendorWHCourierMappingDeletePopupComponent,
    VendorWHCourierMappingDeleteDialogComponent,
    vendorWHCourierMappingRoute,
    vendorWHCourierMappingPopupRoute
} from './';

const ENTITY_STATES = [...vendorWHCourierMappingRoute, ...vendorWHCourierMappingPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VendorWHCourierMappingComponent,
        VendorWHCourierMappingDetailComponent,
        VendorWHCourierMappingUpdateComponent,
        VendorWHCourierMappingDeleteDialogComponent,
        VendorWHCourierMappingDeletePopupComponent
    ],
    entryComponents: [
        VendorWHCourierMappingComponent,
        VendorWHCourierMappingUpdateComponent,
        VendorWHCourierMappingDeleteDialogComponent,
        VendorWHCourierMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsVendorWHCourierMappingModule {}
