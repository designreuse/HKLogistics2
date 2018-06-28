import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICourierGroup } from 'app/shared/model/courier-group.model';
import { Principal } from 'app/core';
import { CourierGroupService } from './courier-group.service';

@Component({
    selector: 'jhi-courier-group',
    templateUrl: './courier-group.component.html'
})
export class CourierGroupComponent implements OnInit, OnDestroy {
    courierGroups: ICourierGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private courierGroupService: CourierGroupService,
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
            this.courierGroupService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICourierGroup[]>) => (this.courierGroups = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.courierGroupService.query().subscribe(
            (res: HttpResponse<ICourierGroup[]>) => {
                this.courierGroups = res.body;
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
        this.registerChangeInCourierGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICourierGroup) {
        return item.id;
    }

    registerChangeInCourierGroups() {
        this.eventSubscriber = this.eventManager.subscribe('courierGroupListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
