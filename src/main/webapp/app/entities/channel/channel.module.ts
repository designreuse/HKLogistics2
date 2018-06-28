import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HkLogisticsSharedModule } from 'app/shared';
import {
    ChannelComponent,
    ChannelDetailComponent,
    ChannelUpdateComponent,
    ChannelDeletePopupComponent,
    ChannelDeleteDialogComponent,
    channelRoute,
    channelPopupRoute
} from './';

const ENTITY_STATES = [...channelRoute, ...channelPopupRoute];

@NgModule({
    imports: [HkLogisticsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChannelComponent,
        ChannelDetailComponent,
        ChannelUpdateComponent,
        ChannelDeleteDialogComponent,
        ChannelDeletePopupComponent
    ],
    entryComponents: [ChannelComponent, ChannelUpdateComponent, ChannelDeleteDialogComponent, ChannelDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HkLogisticsChannelModule {}
