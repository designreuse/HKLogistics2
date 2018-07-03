import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';
import { Principal } from 'app/core';
import { VendorWHCourierMappingService } from './vendor-wh-courier-mapping.service';
import { ICourier } from 'app/shared/model/courier.model';
import { CourierService } from 'app/entities/courier';

@Component({
    selector: 'jhi-vendor-wh-courier-mapping',
    templateUrl: './vendor-wh-courier-mapping.component.html'
})
export class VendorWHCourierMappingComponent implements OnInit, OnDestroy {
    vendorWHCourierMappings: IVendorWHCourierMapping[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    vendorShortCode: string;
    warehouse: number;
    couriers: ICourier[];
    courierId: number;

    constructor(
        private vendorWHCourierMappingService: VendorWHCourierMappingService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private courierService: CourierService,
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.vendorShortCode || this.warehouse || this.courierId) {
            this.vendorWHCourierMappingService
            .filter(this.vendorShortCode, this.warehouse, this.courierId)
                .subscribe(
                    (res: HttpResponse<IVendorWHCourierMapping[]>) => (this.vendorWHCourierMappings = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.vendorWHCourierMappingService.query().subscribe(
            (res: HttpResponse<IVendorWHCourierMapping[]>) => {
                this.vendorWHCourierMappings = res.body;
                this.currentSearch = '';
                this.vendorShortCode = '';
                this.courierId = null;
                this.warehouse = null;
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
        this.courierService.query().subscribe(
            (res: HttpResponse<ICourier[]>) => {
                this.couriers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVendorWHCourierMappings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVendorWHCourierMapping) {
        return item.id;
    }

    registerChangeInVendorWHCourierMappings() {
        this.eventSubscriber = this.eventManager.subscribe('vendorWHCourierMappingListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    filter() {
        console.log(this.vendorShortCode, this.warehouse, this.courierId);
        if ( !this.vendorShortCode && !this.warehouse && !this.courierId) {
                alert("Please select filter");
        } else {
                 this.vendorWHCourierMappingService.filter(this.vendorShortCode, this.warehouse, this.courierId).subscribe(
                    (res: HttpResponse<IVendorWHCourierMapping[]>) => (this.vendorWHCourierMappings = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
        return;
    }

    clearFilter() {
        this.vendorShortCode = '';
        this.warehouse = null;
        this.courierId = null;
        this.loadAll();
    }

    
    trackCourierById(index: number, item: ICourier) {
        return item.id;
    }
}
