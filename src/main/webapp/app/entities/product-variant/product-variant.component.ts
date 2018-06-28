import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductVariant } from 'app/shared/model/product-variant.model';
import { Principal } from 'app/core';
import { ProductVariantService } from './product-variant.service';

@Component({
    selector: 'jhi-product-variant',
    templateUrl: './product-variant.component.html'
})
export class ProductVariantComponent implements OnInit, OnDestroy {
    productVariants: IProductVariant[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private productVariantService: ProductVariantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.productVariantService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IProductVariant[]>) => (this.productVariants = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.productVariantService.query().subscribe(
            (res: HttpResponse<IProductVariant[]>) => {
                this.productVariants = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductVariants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProductVariant) {
        return item.id;
    }

    registerChangeInProductVariants() {
        this.eventSubscriber = this.eventManager.subscribe('productVariantListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
