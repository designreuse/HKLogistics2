import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { HkLogisticsSharedModule } from 'app/shared';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { CommonModule } from '@angular/common';
import {
    ShipmentComponent,
    shipmentRoute
} from './';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const ENTITY_STATES = [...shipmentRoute];

@NgModule({
    imports: [ HkLogisticsSharedModule,RouterModule.forChild(ENTITY_STATES), OwlDateTimeModule,
        OwlNativeDateTimeModule,BrowserAnimationsModule,FormsModule,ReactiveFormsModule,CommonModule],
    declarations: [ShipmentComponent],
    entryComponents: [ShipmentComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsShipmentModule { }
