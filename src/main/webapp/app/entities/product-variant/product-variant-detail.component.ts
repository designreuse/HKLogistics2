import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductVariant } from 'app/shared/model/product-variant.model';

@Component({
    selector: 'jhi-product-variant-detail',
    templateUrl: './product-variant-detail.component.html'
})
export class ProductVariantDetailComponent implements OnInit {
    productVariant: IProductVariant;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productVariant }) => {
            this.productVariant = productVariant;
        });
    }

    previousState() {
        window.history.back();
    }
}
