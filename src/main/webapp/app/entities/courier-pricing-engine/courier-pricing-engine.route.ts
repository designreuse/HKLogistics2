import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { CourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';
import { CourierPricingEngineService } from './courier-pricing-engine.service';
import { CourierPricingEngineComponent } from './courier-pricing-engine.component';
import { CourierPricingEngineDetailComponent } from './courier-pricing-engine-detail.component';
import { CourierPricingEngineUpdateComponent } from './courier-pricing-engine-update.component';
import { CourierPricingEngineDeletePopupComponent } from './courier-pricing-engine-delete-dialog.component';
import { ICourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';

@Injectable({ providedIn: 'root' })
export class CourierPricingEngineResolve implements Resolve<ICourierPricingEngine> {
    constructor(private service: CourierPricingEngineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((courierPricingEngine: HttpResponse<CourierPricingEngine>) => courierPricingEngine.body);
        }
        return Observable.of(new CourierPricingEngine());
    }
}

export const courierPricingEngineRoute: Routes = [
    {
        path: 'courier-pricing-engine',
        component: CourierPricingEngineComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierPricingEngines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-pricing-engine/:id/view',
        component: CourierPricingEngineDetailComponent,
        resolve: {
            courierPricingEngine: CourierPricingEngineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierPricingEngines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-pricing-engine/new',
        component: CourierPricingEngineUpdateComponent,
        resolve: {
            courierPricingEngine: CourierPricingEngineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierPricingEngines'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courier-pricing-engine/:id/edit',
        component: CourierPricingEngineUpdateComponent,
        resolve: {
            courierPricingEngine: CourierPricingEngineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierPricingEngines'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courierPricingEnginePopupRoute: Routes = [
    {
        path: 'courier-pricing-engine/:id/delete',
        component: CourierPricingEngineDeletePopupComponent,
        resolve: {
            courierPricingEngine: CourierPricingEngineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CourierPricingEngines'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
