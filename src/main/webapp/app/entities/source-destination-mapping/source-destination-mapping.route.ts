import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { SourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';
import { SourceDestinationMappingService } from './source-destination-mapping.service';
import { SourceDestinationMappingComponent } from './source-destination-mapping.component';
import { SourceDestinationMappingDetailComponent } from './source-destination-mapping-detail.component';
import { SourceDestinationMappingUpdateComponent } from './source-destination-mapping-update.component';
import { SourceDestinationMappingDeletePopupComponent } from './source-destination-mapping-delete-dialog.component';
import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';

@Injectable({ providedIn: 'root' })
export class SourceDestinationMappingResolve implements Resolve<ISourceDestinationMapping> {
    constructor(private service: SourceDestinationMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .map((sourceDestinationMapping: HttpResponse<SourceDestinationMapping>) => sourceDestinationMapping.body);
        }
        return Observable.of(new SourceDestinationMapping());
    }
}

export const sourceDestinationMappingRoute: Routes = [
    {
        path: 'source-destination-mapping',
        component: SourceDestinationMappingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDestinationMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-destination-mapping/:id/view',
        component: SourceDestinationMappingDetailComponent,
        resolve: {
            sourceDestinationMapping: SourceDestinationMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDestinationMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-destination-mapping/new',
        component: SourceDestinationMappingUpdateComponent,
        resolve: {
            sourceDestinationMapping: SourceDestinationMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDestinationMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'source-destination-mapping/:id/edit',
        component: SourceDestinationMappingUpdateComponent,
        resolve: {
            sourceDestinationMapping: SourceDestinationMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDestinationMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourceDestinationMappingPopupRoute: Routes = [
    {
        path: 'source-destination-mapping/:id/delete',
        component: SourceDestinationMappingDeletePopupComponent,
        resolve: {
            sourceDestinationMapping: SourceDestinationMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceDestinationMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
