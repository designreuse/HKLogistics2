import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { CourierChannel } from 'app/shared/model/courier-channel.model';
import { CourierChannelService } from './courier-channel.service';
import { CourierChannelComponent } from './courier-channel.component';
import { CourierChannelDetailComponent } from './courier-channel-detail.component';
import { CourierChannelUpdateComponent } from './courier-channel-update.component';
import { CourierChannelDeletePopupComponent } from './courier-channel-delete-dialog.component';
import { ICourierChannel } from 'app/shared/model/courier-channel.model';

@Injectable({ providedIn: 'root' })
export class CourierChannelResolve implements Resolve<ICourierChannel> {
    constructor(private service: CourierChannelService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((courierChannel: HttpResponse<CourierChannel>) => courierChannel.body);
        }
        return Observable.of(new CourierChannel());
    }
}

export const courierChannelRoute: Routes = [
    {
        path: 'courier-channel',
        component: CourierChannelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierChannels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-channel/:id/view',
        component: CourierChannelDetailComponent,
        resolve: {
            courierChannel: CourierChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierChannels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-channel/new',
        component: CourierChannelUpdateComponent,
        resolve: {
            courierChannel: CourierChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierChannels'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-channel/:id/edit',
        component: CourierChannelUpdateComponent,
        resolve: {
            courierChannel: CourierChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierChannels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courierChannelPopupRoute: Routes = [
    {
        path: 'courier-channel/:id/delete',
        component: CourierChannelDeletePopupComponent,
        resolve: {
            courierChannel: CourierChannelResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierChannels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
