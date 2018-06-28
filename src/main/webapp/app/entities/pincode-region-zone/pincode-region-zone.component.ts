import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';
import { Principal } from 'app/core';
import { PincodeRegionZoneService } from './pincode-region-zone.service';

@Component({
    selector: 'jhi-pincode-region-zone',
    templateUrl: './pincode-region-zone.component.html'
})
export class PincodeRegionZoneComponent implements OnInit, OnDestroy {
    pincodeRegionZones: IPincodeRegionZone[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private pincodeRegionZoneService: PincodeRegionZoneService,
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
            this.pincodeRegionZoneService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPincodeRegionZone[]>) => (this.pincodeRegionZones = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.pincodeRegionZoneService.query().subscribe(
            (res: HttpResponse<IPincodeRegionZone[]>) => {
                this.pincodeRegionZones = res.body;
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
        this.registerChangeInPincodeRegionZones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPincodeRegionZone) {
        return item.id;
    }

    registerChangeInPincodeRegionZones() {
        this.eventSubscriber = this.eventManager.subscribe('pincodeRegionZoneListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
