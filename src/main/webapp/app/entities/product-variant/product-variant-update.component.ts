import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IProductVariant } from 'app/shared/model/product-variant.model';
import { ProductVariantService } from './product-variant.service';

@Component({
    selector: 'jhi-product-variant-update',
    templateUrl: './product-variant-update.component.html'
})
export class ProductVariantUpdateComponent implements OnInit {
    private _productVariant: IProductVariant;
    isSaving: boolean;

    constructor(private productVariantService: ProductVariantService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productVariant }) => {
            this.productVariant = productVariant;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productVariant.id !== undefined) {
            this.subscribeToSaveResponse(this.productVariantService.update(this.productVariant));
        } else {
            this.subscribeToSaveResponse(this.productVariantService.create(this.productVariant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProductVariant>>) {
        result.subscribe((res: HttpResponse<IProductVariant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get productVariant() {
        return this._productVariant;
    }

    set productVariant(productVariant: IProductVariant) {
        this._productVariant = productVariant;
    }
}
