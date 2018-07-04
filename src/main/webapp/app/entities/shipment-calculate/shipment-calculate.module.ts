import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { HkLogisticsSharedModule } from 'app/shared';
import { CommonModule } from '@angular/common';
import {
    ShipmentCalculateComponent,
    shipmentCalculateRoute
} from './';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...shipmentCalculateRoute];

@NgModule({
    imports: [ HkLogisticsSharedModule,RouterModule.forChild(ENTITY_STATES),
        BrowserAnimationsModule,FormsModule,ReactiveFormsModule,CommonModule],
    declarations: [ShipmentCalculateComponent],
    entryComponents: [ShipmentCalculateComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsShipmentCalculateModule { }
