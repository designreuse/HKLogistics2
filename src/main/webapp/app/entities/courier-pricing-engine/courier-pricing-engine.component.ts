import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';
import { Principal } from 'app/core';
import { CourierPricingEngineService } from './courier-pricing-engine.service';
import { ICourier } from 'app/shared/model/courier.model';
import { CourierService } from 'app/entities/courier';

@Component({
    selector: 'jhi-courier-pricing-engine',
    templateUrl: './courier-pricing-engine.component.html'
})
export class CourierPricingEngineComponent implements OnInit, OnDestroy {
    courierPricingEngines: ICourierPricingEngine[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    couriers: ICourier[];
    courierId: number;

    constructor(
        private courierPricingEngineService: CourierPricingEngineService,
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
        if (this.courierId) {
            this.courierPricingEngineService
            .filter(this.courierId)
                .subscribe(
                    (res: HttpResponse<ICourierPricingEngine[]>) => (this.courierPricingEngines = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.courierPricingEngineService.query().subscribe(
            (res: HttpResponse<ICourierPricingEngine[]>) => {
                this.courierPricingEngines = res.body;
                this.currentSearch = '';
                this.courierId = null;
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
        this.registerChangeInCourierPricingEngines();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICourierPricingEngine) {
        return item.id;
    }

    registerChangeInCourierPricingEngines() {
        this.eventSubscriber = this.eventManager.subscribe('courierPricingEngineListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    filter() {
        console.log(this.courierId);
        if ( !this.courierId) {
                alert("Please select filter");
        } else {
                 this.courierPricingEngineService.filter(this.courierId).subscribe(
                    (res: HttpResponse<ICourierPricingEngine[]>) => (this.courierPricingEngines = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
        return;
    }

    clearFilter() {
        this.courierId = null;
        this.loadAll();
    }

    
    trackCourierById(index: number, item: ICourier) {
        return item.id;
    }
}
