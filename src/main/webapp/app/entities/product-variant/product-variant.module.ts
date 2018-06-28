import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    ProductVariantComponent,
    ProductVariantDetailComponent,
    ProductVariantUpdateComponent,
    ProductVariantDeletePopupComponent,
    ProductVariantDeleteDialogComponent,
    productVariantRoute,
    productVariantPopupRoute
} from './';

const ENTITY_STATES = [...productVariantRoute, ...productVariantPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductVariantComponent,
        ProductVariantDetailComponent,
        ProductVariantUpdateComponent,
        ProductVariantDeleteDialogComponent,
        ProductVariantDeletePopupComponent
    ],
    entryComponents: [
        ProductVariantComponent,
        ProductVariantUpdateComponent,
        ProductVariantDeleteDialogComponent,
        ProductVariantDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsProductVariantModule {}
