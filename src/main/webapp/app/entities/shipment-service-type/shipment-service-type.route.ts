import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { ShipmentServiceType } from 'app/shared/model/shipment-service-type.model';
import { ShipmentServiceTypeService } from './shipment-service-type.service';
import { ShipmentServiceTypeComponent } from './shipment-service-type.component';
import { ShipmentServiceTypeDetailComponent } from './shipment-service-type-detail.component';
import { ShipmentServiceTypeUpdateComponent } from './shipment-service-type-update.component';
import { ShipmentServiceTypeDeletePopupComponent } from './shipment-service-type-delete-dialog.component';
import { IShipmentServiceType } from 'app/shared/model/shipment-service-type.model';

@Injectable({ providedIn: 'root' })
export class ShipmentServiceTypeResolve implements Resolve<IShipmentServiceType> {
    constructor(private service: ShipmentServiceTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((shipmentServiceType: HttpResponse<ShipmentServiceType>) => shipmentServiceType.body);
        }
        return Observable.of(new ShipmentServiceType());
    }
}

export const shipmentServiceTypeRoute: Routes = [
    {
        path: 'shipment-service-type',
        component: ShipmentServiceTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShipmentServiceTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shipment-service-type/:id/view',
        component: ShipmentServiceTypeDetailComponent,
        resolve: {
            shipmentServiceType: ShipmentServiceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShipmentServiceTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shipment-service-type/new',
        component: ShipmentServiceTypeUpdateComponent,
        resolve: {
            shipmentServiceType: ShipmentServiceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShipmentServiceTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'shipment-service-type/:id/edit',
        component: ShipmentServiceTypeUpdateComponent,
        resolve: {
            shipmentServiceType: ShipmentServiceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShipmentServiceTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shipmentServiceTypePopupRoute: Routes = [
    {
        path: 'shipment-service-type/:id/delete',
        component: ShipmentServiceTypeDeletePopupComponent,
        resolve: {
            shipmentServiceType: ShipmentServiceTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ShipmentServiceTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
