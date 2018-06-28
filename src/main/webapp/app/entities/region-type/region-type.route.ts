import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { RegionType } from 'app/shared/model/region-type.model';
import { RegionTypeService } from './region-type.service';
import { RegionTypeComponent } from './region-type.component';
import { RegionTypeDetailComponent } from './region-type-detail.component';
import { RegionTypeUpdateComponent } from './region-type-update.component';
import { RegionTypeDeletePopupComponent } from './region-type-delete-dialog.component';
import { IRegionType } from 'app/shared/model/region-type.model';

@Injectable({ providedIn: 'root' })
export class RegionTypeResolve implements Resolve<IRegionType> {
    constructor(private service: RegionTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((regionType: HttpResponse<RegionType>) => regionType.body);
        }
        return Observable.of(new RegionType());
    }
}

export const regionTypeRoute: Routes = [
    {
        path: 'region-type',
        component: RegionTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegionTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'region-type/:id/view',
        component: RegionTypeDetailComponent,
        resolve: {
            regionType: RegionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegionTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'region-type/new',
        component: RegionTypeUpdateComponent,
        resolve: {
            regionType: RegionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegionTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'region-type/:id/edit',
        component: RegionTypeUpdateComponent,
        resolve: {
            regionType: RegionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegionTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regionTypePopupRoute: Routes = [
    {
        path: 'region-type/:id/delete',
        component: RegionTypeDeletePopupComponent,
        resolve: {
            regionType: RegionTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RegionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
