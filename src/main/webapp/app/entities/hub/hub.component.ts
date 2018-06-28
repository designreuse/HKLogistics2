import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHub } from 'app/shared/model/hub.model';
import { Principal } from 'app/core';
import { HubService } from './hub.service';

@Component({
    selector: 'jhi-hub',
    templateUrl: './hub.component.html'
})
export class HubComponent implements OnInit, OnDestroy {
    hubs: IHub[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private hubService: HubService,
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
            this.hubService
                .search({
                    query: this.currentSearch
                })
                .subscribe((res: HttpResponse<IHub[]>) => (this.hubs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.hubService.query().subscribe(
            (res: HttpResponse<IHub[]>) => {
                this.hubs = res.body;
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
        this.registerChangeInHubs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHub) {
        return item.id;
    }

    registerChangeInHubs() {
        this.eventSubscriber = this.eventManager.subscribe('hubListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
