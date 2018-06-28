import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';
import { Principal } from 'app/core';
import { PincodeCourierMappingService } from './pincode-courier-mapping.service';

@Component({
    selector: 'jhi-pincode-courier-mapping',
    templateUrl: './pincode-courier-mapping.component.html'
})
export class PincodeCourierMappingComponent implements OnInit, OnDestroy {
    pincodeCourierMappings: IPincodeCourierMapping[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private pincodeCourierMappingService: PincodeCourierMappingService,
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
            this.pincodeCourierMappingService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IPincodeCourierMapping[]>) => (this.pincodeCourierMappings = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.pincodeCourierMappingService.query().subscribe(
            (res: HttpResponse<IPincodeCourierMapping[]>) => {
                this.pincodeCourierMappings = res.body;
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
        this.registerChangeInPincodeCourierMappings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPincodeCourierMapping) {
        return item.id;
    }

    registerChangeInPincodeCourierMappings() {
        this.eventSubscriber = this.eventManager.subscribe('pincodeCourierMappingListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
