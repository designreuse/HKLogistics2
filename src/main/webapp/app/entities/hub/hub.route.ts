import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Hub } from 'app/shared/model/hub.model';
import { HubService } from './hub.service';
import { HubComponent } from './hub.component';
import { HubDetailComponent } from './hub-detail.component';
import { HubUpdateComponent } from './hub-update.component';
import { HubDeletePopupComponent } from './hub-delete-dialog.component';
import { IHub } from 'app/shared/model/hub.model';

@Injectable({ providedIn: 'root' })
export class HubResolve implements Resolve<IHub> {
    constructor(private service: HubService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((hub: HttpResponse<Hub>) => hub.body);
        }
        return Observable.of(new Hub());
    }
}

export const hubRoute: Routes = [
    {
        path: 'hub',
        component: HubComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hubs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hub/:id/view',
        component: HubDetailComponent,
        resolve: {
            hub: HubResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hubs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hub/new',
        component: HubUpdateComponent,
        resolve: {
            hub: HubResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hubs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hub/:id/edit',
        component: HubUpdateComponent,
        resolve: {
            hub: HubResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hubs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hubPopupRoute: Routes = [
    {
        path: 'hub/:id/delete',
        component: HubDeletePopupComponent,
        resolve: {
            hub: HubResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Hubs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
