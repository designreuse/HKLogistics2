import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICourierChannel } from 'app/shared/model/courier-channel.model';
import { Principal } from 'app/core';
import { CourierChannelService } from './courier-channel.service';

@Component({
    selector: 'jhi-courier-channel',
    templateUrl: './courier-channel.component.html'
})
export class CourierChannelComponent implements OnInit, OnDestroy {
    courierChannels: ICourierChannel[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private courierChannelService: CourierChannelService,
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
            this.courierChannelService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICourierChannel[]>) => (this.courierChannels = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.courierChannelService.query().subscribe(
            (res: HttpResponse<ICourierChannel[]>) => {
                this.courierChannels = res.body;
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
        this.registerChangeInCourierChannels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICourierChannel) {
        return item.id;
    }

    registerChangeInCourierChannels() {
        this.eventSubscriber = this.eventManager.subscribe('courierChannelListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
