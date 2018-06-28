import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAwb } from 'app/shared/model/awb.model';
import { Principal } from 'app/core';
import { AwbService } from './awb.service';

@Component({
    selector: 'jhi-awb',
    templateUrl: './awb.component.html'
})
export class AwbComponent implements OnInit, OnDestroy {
    awbs: IAwb[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private awbService: AwbService,
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
            this.awbService
                .search({
                    query: this.currentSearch
                })
                .subscribe((res: HttpResponse<IAwb[]>) => (this.awbs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.awbService.query().subscribe(
            (res: HttpResponse<IAwb[]>) => {
                this.awbs = res.body;
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
        this.registerChangeInAwbs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAwb) {
        return item.id;
    }

    registerChangeInAwbs() {
        this.eventSubscriber = this.eventManager.subscribe('awbListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
