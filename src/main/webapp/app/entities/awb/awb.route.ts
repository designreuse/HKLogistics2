import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Awb } from 'app/shared/model/awb.model';
import { AwbService } from './awb.service';
import { AwbComponent } from './awb.component';
import { AwbDetailComponent } from './awb-detail.component';
import { AwbUpdateComponent } from './awb-update.component';
import { AwbDeletePopupComponent } from './awb-delete-dialog.component';
import { IAwb } from 'app/shared/model/awb.model';

@Injectable({ providedIn: 'root' })
export class AwbResolve implements Resolve<IAwb> {
    constructor(private service: AwbService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((awb: HttpResponse<Awb>) => awb.body);
        }
        return Observable.of(new Awb());
    }
}

export const awbRoute: Routes = [
    {
        path: 'awb',
        component: AwbComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awbs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'awb/:id/view',
        component: AwbDetailComponent,
        resolve: {
            awb: AwbResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awbs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'awb/new',
        component: AwbUpdateComponent,
        resolve: {
            awb: AwbResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awbs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'awb/:id/edit',
        component: AwbUpdateComponent,
        resolve: {
            awb: AwbResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awbs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const awbPopupRoute: Routes = [
    {
        path: 'awb/:id/delete',
        component: AwbDeletePopupComponent,
        resolve: {
            awb: AwbResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awbs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
