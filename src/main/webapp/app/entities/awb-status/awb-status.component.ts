import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAwbStatus } from 'app/shared/model/awb-status.model';
import { Principal } from 'app/core';
import { AwbStatusService } from './awb-status.service';

@Component({
    selector: 'jhi-awb-status',
    templateUrl: './awb-status.component.html'
})
export class AwbStatusComponent implements OnInit, OnDestroy {
    awbStatuses: IAwbStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private awbStatusService: AwbStatusService,
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
            this.awbStatusService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IAwbStatus[]>) => (this.awbStatuses = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.awbStatusService.query().subscribe(
            (res: HttpResponse<IAwbStatus[]>) => {
                this.awbStatuses = res.body;
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
        this.registerChangeInAwbStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAwbStatus) {
        return item.id;
    }

    registerChangeInAwbStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('awbStatusListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
