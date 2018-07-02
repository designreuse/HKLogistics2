import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Shipment } from 'app/shared/model/shipment.model';
import { ShipmentService } from './shipment.service';
import { ShipmentComponent } from './shipment.component';
import { IShipment } from 'app/shared/model/shipment.model';

@Injectable({ providedIn: 'root' })
export class ShipmentResolve implements Resolve<IShipment> {
    constructor(private service: ShipmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        return Observable.of(new Shipment());
    }
}

export const shipmentRoute: Routes = [
    {
        path: 'shipment',
        component: ShipmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shipment'
        }
        ,
        canActivate: [UserRouteAccessService]
    }
];
