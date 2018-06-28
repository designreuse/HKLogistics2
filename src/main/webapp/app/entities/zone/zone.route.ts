import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Zone } from 'app/shared/model/zone.model';
import { ZoneService } from './zone.service';
import { ZoneComponent } from './zone.component';
import { ZoneDetailComponent } from './zone-detail.component';
import { ZoneUpdateComponent } from './zone-update.component';
import { ZoneDeletePopupComponent } from './zone-delete-dialog.component';
import { IZone } from 'app/shared/model/zone.model';

@Injectable({ providedIn: 'root' })
export class ZoneResolve implements Resolve<IZone> {
    constructor(private service: ZoneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((zone: HttpResponse<Zone>) => zone.body);
        }
        return Observable.of(new Zone());
    }
}

export const zoneRoute: Routes = [
    {
        path: 'zone',
        component: ZoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Zones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'zone/:id/view',
        component: ZoneDetailComponent,
        resolve: {
            zone: ZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Zones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'zone/new',
        component: ZoneUpdateComponent,
        resolve: {
            zone: ZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Zones'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'zone/:id/edit',
        component: ZoneUpdateComponent,
        resolve: {
            zone: ZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Zones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const zonePopupRoute: Routes = [
    {
        path: 'zone/:id/delete',
        component: ZoneDeletePopupComponent,
        resolve: {
            zone: ZoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Zones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
