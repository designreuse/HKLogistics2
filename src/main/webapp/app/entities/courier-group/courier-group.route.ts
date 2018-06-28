import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { CourierGroup } from 'app/shared/model/courier-group.model';
import { CourierGroupService } from './courier-group.service';
import { CourierGroupComponent } from './courier-group.component';
import { CourierGroupDetailComponent } from './courier-group-detail.component';
import { CourierGroupUpdateComponent } from './courier-group-update.component';
import { CourierGroupDeletePopupComponent } from './courier-group-delete-dialog.component';
import { ICourierGroup } from 'app/shared/model/courier-group.model';

@Injectable({ providedIn: 'root' })
export class CourierGroupResolve implements Resolve<ICourierGroup> {
    constructor(private service: CourierGroupService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((courierGroup: HttpResponse<CourierGroup>) => courierGroup.body);
        }
        return Observable.of(new CourierGroup());
    }
}

export const courierGroupRoute: Routes = [
    {
        path: 'courier-group',
        component: CourierGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-group/:id/view',
        component: CourierGroupDetailComponent,
        resolve: {
            courierGroup: CourierGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-group/new',
        component: CourierGroupUpdateComponent,
        resolve: {
            courierGroup: CourierGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierGroups'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-group/:id/edit',
        component: CourierGroupUpdateComponent,
        resolve: {
            courierGroup: CourierGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierGroups'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courierGroupPopupRoute: Routes = [
    {
        path: 'courier-group/:id/delete',
        component: CourierGroupDeletePopupComponent,
        resolve: {
            courierGroup: CourierGroupResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierGroups'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
