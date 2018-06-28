import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { PincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';
import { PincodeRegionZoneService } from './pincode-region-zone.service';
import { PincodeRegionZoneComponent } from './pincode-region-zone.component';
import { PincodeRegionZoneDetailComponent } from './pincode-region-zone-detail.component';
import { PincodeRegionZoneUpdateComponent } from './pincode-region-zone-update.component';
import { PincodeRegionZoneDeletePopupComponent } from './pincode-region-zone-delete-dialog.component';
import { IPincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';

@Injectable({ providedIn: 'root' })
export class PincodeRegionZoneResolve implements Resolve<IPincodeRegionZone> {
    constructor(private service: PincodeRegionZoneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((pincodeRegionZone: HttpResponse<PincodeRegionZone>) => pincodeRegionZone.body);
        }
        return Observable.of(new PincodeRegionZone());
    }
}

export const pincodeRegionZoneRoute: Routes = [
    {
        path: 'pincode-region-zone',
        component: PincodeRegionZoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeRegionZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode-region-zone/:id/view',
        component: PincodeRegionZoneDetailComponent,
        resolve: {
            pincodeRegionZone: PincodeRegionZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeRegionZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode-region-zone/new',
        component: PincodeRegionZoneUpdateComponent,
        resolve: {
            pincodeRegionZone: PincodeRegionZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeRegionZones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode-region-zone/:id/edit',
        component: PincodeRegionZoneUpdateComponent,
        resolve: {
            pincodeRegionZone: PincodeRegionZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeRegionZones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pincodeRegionZonePopupRoute: Routes = [
    {
        path: 'pincode-region-zone/:id/delete',
        component: PincodeRegionZoneDeletePopupComponent,
        resolve: {
            pincodeRegionZone: PincodeRegionZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeRegionZones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
