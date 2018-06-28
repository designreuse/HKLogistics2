import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';
import { Principal } from 'app/core';
import { CourierPricingEngineService } from './courier-pricing-engine.service';

@Component({
    selector: 'jhi-courier-pricing-engine',
    templateUrl: './courier-pricing-engine.component.html'
})
export class CourierPricingEngineComponent implements OnInit, OnDestroy {
    courierPricingEngines: ICourierPricingEngine[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private courierPricingEngineService: CourierPricingEngineService,
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
            this.courierPricingEngineService
                .search({
                    query: this.currentSearch
                })
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
}
