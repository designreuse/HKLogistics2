import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { AwbStatus } from 'app/shared/model/awb-status.model';
import { AwbStatusService } from './awb-status.service';
import { AwbStatusComponent } from './awb-status.component';
import { AwbStatusDetailComponent } from './awb-status-detail.component';
import { AwbStatusUpdateComponent } from './awb-status-update.component';
import { AwbStatusDeletePopupComponent } from './awb-status-delete-dialog.component';
import { IAwbStatus } from 'app/shared/model/awb-status.model';

@Injectable({ providedIn: 'root' })
export class AwbStatusResolve implements Resolve<IAwbStatus> {
    constructor(private service: AwbStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((awbStatus: HttpResponse<AwbStatus>) => awbStatus.body);
        }
        return Observable.of(new AwbStatus());
    }
}

export const awbStatusRoute: Routes = [
    {
        path: 'awb-status',
        component: AwbStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AwbStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'awb-status/:id/view',
        component: AwbStatusDetailComponent,
        resolve: {
            awbStatus: AwbStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AwbStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'awb-status/new',
        component: AwbStatusUpdateComponent,
        resolve: {
            awbStatus: AwbStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AwbStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'awb-status/:id/edit',
        component: AwbStatusUpdateComponent,
        resolve: {
            awbStatus: AwbStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AwbStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const awbStatusPopupRoute: Routes = [
    {
        path: 'awb-status/:id/delete',
        component: AwbStatusDeletePopupComponent,
        resolve: {
            awbStatus: AwbStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AwbStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
