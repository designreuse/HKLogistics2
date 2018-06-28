import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { PincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';
import { PincodeCourierMappingService } from './pincode-courier-mapping.service';
import { PincodeCourierMappingComponent } from './pincode-courier-mapping.component';
import { PincodeCourierMappingDetailComponent } from './pincode-courier-mapping-detail.component';
import { PincodeCourierMappingUpdateComponent } from './pincode-courier-mapping-update.component';
import { PincodeCourierMappingDeletePopupComponent } from './pincode-courier-mapping-delete-dialog.component';
import { IPincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';

@Injectable({ providedIn: 'root' })
export class PincodeCourierMappingResolve implements Resolve<IPincodeCourierMapping> {
    constructor(private service: PincodeCourierMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((pincodeCourierMapping: HttpResponse<PincodeCourierMapping>) => pincodeCourierMapping.body);
        }
        return Observable.of(new PincodeCourierMapping());
    }
}

export const pincodeCourierMappingRoute: Routes = [
    {
        path: 'pincode-courier-mapping',
        component: PincodeCourierMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode-courier-mapping/:id/view',
        component: PincodeCourierMappingDetailComponent,
        resolve: {
            pincodeCourierMapping: PincodeCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode-courier-mapping/new',
        component: PincodeCourierMappingUpdateComponent,
        resolve: {
            pincodeCourierMapping: PincodeCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode-courier-mapping/:id/edit',
        component: PincodeCourierMappingUpdateComponent,
        resolve: {
            pincodeCourierMapping: PincodeCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pincodeCourierMappingPopupRoute: Routes = [
    {
        path: 'pincode-courier-mapping/:id/delete',
        component: PincodeCourierMappingDeletePopupComponent,
        resolve: {
            pincodeCourierMapping: PincodeCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PincodeCourierMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
