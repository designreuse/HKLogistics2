import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IZone } from 'app/shared/model/zone.model';
import { Principal } from 'app/core';
import { ZoneService } from './zone.service';

@Component({
    selector: 'jhi-zone',
    templateUrl: './zone.component.html'
})
export class ZoneComponent implements OnInit, OnDestroy {
    zones: IZone[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private zoneService: ZoneService,
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
            this.zoneService
                .search({
                    query: this.currentSearch
                })
                .subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.zoneService.query().subscribe(
            (res: HttpResponse<IZone[]>) => {
                this.zones = res.body;
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
        this.registerChangeInZones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IZone) {
        return item.id;
    }

    registerChangeInZones() {
        this.eventSubscriber = this.eventManager.subscribe('zoneListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
