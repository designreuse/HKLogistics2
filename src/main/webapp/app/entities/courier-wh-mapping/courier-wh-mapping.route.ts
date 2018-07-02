import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { CourierWHMappingService } from './courier-wh-mapping.service';
import { CourierWHMappingComponent } from './courier-wh-mapping.component';

@Injectable({ providedIn: 'root' })
export class CourierWHMappingResolve implements Resolve<any> {
    constructor(private service: CourierWHMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        
    }
}

export const courierWHMappingRoute: Routes = [
    {
        path: 'courier-wh-mapping',
        component: CourierWHMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'courier-wh-mapping'
        }
        ,
       canActivate: [UserRouteAccessService]
    }
];
