import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IShipmentServiceType } from 'app/shared/model/shipment-service-type.model';
import { Principal } from 'app/core';
import { ShipmentServiceTypeService } from './shipment-service-type.service';

@Component({
    selector: 'jhi-shipment-service-type',
    templateUrl: './shipment-service-type.component.html'
})
export class ShipmentServiceTypeComponent implements OnInit, OnDestroy {
    shipmentServiceTypes: IShipmentServiceType[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private shipmentServiceTypeService: ShipmentServiceTypeService,
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
            this.shipmentServiceTypeService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IShipmentServiceType[]>) => (this.shipmentServiceTypes = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.shipmentServiceTypeService.query().subscribe(
            (res: HttpResponse<IShipmentServiceType[]>) => {
                this.shipmentServiceTypes = res.body;
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
        this.registerChangeInShipmentServiceTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IShipmentServiceType) {
        return item.id;
    }

    registerChangeInShipmentServiceTypes() {
        this.eventSubscriber = this.eventManager.subscribe('shipmentServiceTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
