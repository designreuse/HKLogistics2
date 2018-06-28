import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { State } from 'app/shared/model/state.model';
import { StateService } from './state.service';
import { StateComponent } from './state.component';
import { StateDetailComponent } from './state-detail.component';
import { StateUpdateComponent } from './state-update.component';
import { StateDeletePopupComponent } from './state-delete-dialog.component';
import { IState } from 'app/shared/model/state.model';

@Injectable({ providedIn: 'root' })
export class StateResolve implements Resolve<IState> {
    constructor(private service: StateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((state: HttpResponse<State>) => state.body);
        }
        return Observable.of(new State());
    }
}

export const stateRoute: Routes = [
    {
        path: 'state',
        component: StateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'state/:id/view',
        component: StateDetailComponent,
        resolve: {
            state: StateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'state/new',
        component: StateUpdateComponent,
        resolve: {
            state: StateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'state/:id/edit',
        component: StateUpdateComponent,
        resolve: {
            state: StateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statePopupRoute: Routes = [
    {
        path: 'state/:id/delete',
        component: StateDeletePopupComponent,
        resolve: {
            state: StateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'States'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
