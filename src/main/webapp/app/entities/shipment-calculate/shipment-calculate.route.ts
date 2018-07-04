import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { ShipmentCalculate } from 'app/shared/model/shipment-calculate.model';
import { ShipmentCalculateService } from './shipment-calculate.service';
import { ShipmentCalculateComponent } from './shipment-calculate.component';
import { IShipmentCalculate } from 'app/shared/model/shipment-calculate.model';

@Injectable({ providedIn: 'root' })
export class ShipmentCalculateResolve implements Resolve<IShipmentCalculate> {
    constructor(private service: ShipmentCalculateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        return Observable.of(new ShipmentCalculate());
    }
}

export const shipmentCalculateRoute: Routes = [
    {
        path: 'shipment-calculate',
        component: ShipmentCalculateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'shipment-calculate'
        }
        ,
        canActivate: [UserRouteAccessService]
    }
];
