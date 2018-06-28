import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Courier } from 'app/shared/model/courier.model';
import { CourierService } from './courier.service';
import { CourierComponent } from './courier.component';
import { CourierDetailComponent } from './courier-detail.component';
import { CourierUpdateComponent } from './courier-update.component';
import { CourierDeletePopupComponent } from './courier-delete-dialog.component';
import { ICourier } from 'app/shared/model/courier.model';

@Injectable({ providedIn: 'root' })
export class CourierResolve implements Resolve<ICourier> {
    constructor(private service: CourierService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((courier: HttpResponse<Courier>) => courier.body);
        }
        return Observable.of(new Courier());
    }
}

export const courierRoute: Routes = [
    {
        path: 'courier',
        component: CourierComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Couriers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier/:id/view',
        component: CourierDetailComponent,
        resolve: {
            courier: CourierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Couriers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier/new',
        component: CourierUpdateComponent,
        resolve: {
            courier: CourierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Couriers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier/:id/edit',
        component: CourierUpdateComponent,
        resolve: {
            courier: CourierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Couriers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courierPopupRoute: Routes = [
    {
        path: 'courier/:id/delete',
        component: CourierDeletePopupComponent,
        resolve: {
            courier: CourierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Couriers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
