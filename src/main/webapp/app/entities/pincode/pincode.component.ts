import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPincode } from 'app/shared/model/pincode.model';
import { Principal } from 'app/core';
import { PincodeService } from './pincode.service';

@Component({
    selector: 'jhi-pincode',
    templateUrl: './pincode.component.html'
})
export class PincodeComponent implements OnInit, OnDestroy {
    pincodes: IPincode[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearchName: string;

    constructor(
        private pincodeService: PincodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearchName =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['searchName']
                ? this.activatedRoute.snapshot.params['searchName']
                : '';
    }

    loadAll() {
        if (this.currentSearchName) {
            this.pincodeService
                .searchName({
                    query: this.currentSearchName
                })
                .subscribe(
                    (res: HttpResponse<IPincode[]>) => (this.pincodes = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            console.log('cityId' + this.pincodes[0].pincode);
            return;
        }
        this.pincodeService.query().subscribe(
            (res: HttpResponse<IPincode[]>) => {
                this.pincodes = res.body;
                this.currentSearchName = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    searchName(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearchName = query;
        this.loadAll();
    }

    clear() {
        this.currentSearchName = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPincodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPincode) {
        return item.id;
    }

    registerChangeInPincodes() {
        this.eventSubscriber = this.eventManager.subscribe('pincodeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
