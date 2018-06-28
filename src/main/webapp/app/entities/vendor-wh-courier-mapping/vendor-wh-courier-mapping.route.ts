import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { VendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';
import { VendorWHCourierMappingService } from './vendor-wh-courier-mapping.service';
import { VendorWHCourierMappingComponent } from './vendor-wh-courier-mapping.component';
import { VendorWHCourierMappingDetailComponent } from './vendor-wh-courier-mapping-detail.component';
import { VendorWHCourierMappingUpdateComponent } from './vendor-wh-courier-mapping-update.component';
import { VendorWHCourierMappingDeletePopupComponent } from './vendor-wh-courier-mapping-delete-dialog.component';
import { IVendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';

@Injectable({ providedIn: 'root' })
export class VendorWHCourierMappingResolve implements Resolve<IVendorWHCourierMapping> {
    constructor(private service: VendorWHCourierMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((vendorWHCourierMapping: HttpResponse<VendorWHCourierMapping>) => vendorWHCourierMapping.body);
        }
        return Observable.of(new VendorWHCourierMapping());
    }
}

export const vendorWHCourierMappingRoute: Routes = [
    {
        path: 'vendor-wh-courier-mapping',
        component: VendorWHCourierMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VendorWHCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendor-wh-courier-mapping/:id/view',
        component: VendorWHCourierMappingDetailComponent,
        resolve: {
            vendorWHCourierMapping: VendorWHCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VendorWHCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendor-wh-courier-mapping/new',
        component: VendorWHCourierMappingUpdateComponent,
        resolve: {
            vendorWHCourierMapping: VendorWHCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VendorWHCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vendor-wh-courier-mapping/:id/edit',
        component: VendorWHCourierMappingUpdateComponent,
        resolve: {
            vendorWHCourierMapping: VendorWHCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VendorWHCourierMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vendorWHCourierMappingPopupRoute: Routes = [
    {
        path: 'vendor-wh-courier-mapping/:id/delete',
        component: VendorWHCourierMappingDeletePopupComponent,
        resolve: {
            vendorWHCourierMapping: VendorWHCourierMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'VendorWHCourierMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
