import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { ProductVariant } from 'app/shared/model/product-variant.model';
import { ProductVariantService } from './product-variant.service';
import { ProductVariantComponent } from './product-variant.component';
import { ProductVariantDetailComponent } from './product-variant-detail.component';
import { ProductVariantUpdateComponent } from './product-variant-update.component';
import { ProductVariantDeletePopupComponent } from './product-variant-delete-dialog.component';
import { IProductVariant } from 'app/shared/model/product-variant.model';

@Injectable({ providedIn: 'root' })
export class ProductVariantResolve implements Resolve<IProductVariant> {
    constructor(private service: ProductVariantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((productVariant: HttpResponse<ProductVariant>) => productVariant.body);
        }
        return Observable.of(new ProductVariant());
    }
}

export const productVariantRoute: Routes = [
    {
        path: 'product-variant',
        component: ProductVariantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductVariants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-variant/:id/view',
        component: ProductVariantDetailComponent,
        resolve: {
            productVariant: ProductVariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductVariants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-variant/new',
        component: ProductVariantUpdateComponent,
        resolve: {
            productVariant: ProductVariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductVariants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-variant/:id/edit',
        component: ProductVariantUpdateComponent,
        resolve: {
            productVariant: ProductVariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductVariants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productVariantPopupRoute: Routes = [
    {
        path: 'product-variant/:id/delete',
        component: ProductVariantDeletePopupComponent,
        resolve: {
            productVariant: ProductVariantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProductVariants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
