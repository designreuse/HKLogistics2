import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Pincode } from 'app/shared/model/pincode.model';
import { PincodeService } from './pincode.service';
import { PincodeComponent } from './pincode.component';
import { PincodeDetailComponent } from './pincode-detail.component';
import { PincodeUpdateComponent } from './pincode-update.component';
import { PincodeDeletePopupComponent } from './pincode-delete-dialog.component';
import { IPincode } from 'app/shared/model/pincode.model';

@Injectable({ providedIn: 'root' })
export class PincodeResolve implements Resolve<IPincode> {
    constructor(private service: PincodeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((pincode: HttpResponse<Pincode>) => pincode.body);
        }
        return Observable.of(new Pincode());
    }
}

export const pincodeRoute: Routes = [
    {
        path: 'pincode',
        component: PincodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pincodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode/:id/view',
        component: PincodeDetailComponent,
        resolve: {
            pincode: PincodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pincodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode/new',
        component: PincodeUpdateComponent,
        resolve: {
            pincode: PincodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pincodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pincode/:id/edit',
        component: PincodeUpdateComponent,
        resolve: {
            pincode: PincodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pincodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pincodePopupRoute: Routes = [
    {
        path: 'pincode/:id/delete',
        component: PincodeDeletePopupComponent,
        resolve: {
            pincode: PincodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pincodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
