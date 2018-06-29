import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAwb } from 'app/shared/model/awb.model';
import { Principal } from 'app/core';
import { AwbService } from './awb.service';
import { ICourier } from 'app/shared/model/courier.model';
import { CourierService } from 'app/entities/courier';
import { IAwbStatus } from 'app/shared/model/awb-status.model';
import { AwbStatusService } from 'app/entities/awb-status';

@Component({
    selector: 'jhi-awb',
    templateUrl: './awb.component.html'
})
export class AwbComponent implements OnInit, OnDestroy {
    awbs: IAwb[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    couriers: ICourier[];
    filterOptions: String[];
    awbstatuses: IAwbStatus[];
    awbStatus: IAwbStatus;
    courier: ICourier;

    constructor(
        private awbService: AwbService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private courierService: CourierService,
        private awbStatusService: AwbStatusService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
        this.filterOptions =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['awbCriteria']
                ? this.activatedRoute.snapshot.params['awbCriteria'].split(',')
                : [];
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
        /*if (this.filterOptions && this.filterOptions.length !== 0) {
            this.awbService
                .filter({
                    awbCriteria : this.filterOptions
                });
            console.log('hii');
            return;
        }*/
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

    clearFilter() {
        this.filterOptions = [];
        this.filterOptions[0] = '';
        this.loadAll();
    }

    ngOnInit() {
        this.courierService.query().subscribe(
            (res: HttpResponse<ICourier[]>) => {
                this.couriers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.awbStatusService.query().subscribe(
            (res: HttpResponse<IAwbStatus[]>) => {
                this.awbstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAwbs();
        this.awbStatus = null;
        this.courier = null;
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

    filter(filterOptions: String[]) {
        console.log('controller awb filter');
        if (filterOptions[0] && filterOptions[0] !== '' && !filterOptions[0].toString().startsWith('courierId') ) {
            filterOptions[0] = 'courierId:' + filterOptions[0];
        }
        if (filterOptions[1] && filterOptions[1] !== '' && !filterOptions[1].toString().startsWith('awbStatusId') ) {
            filterOptions[1] = 'awbStatusId:' + filterOptions[1];
        }
        this.filterOptions = filterOptions;
        console.log('couriers');
        this.loadAll();
    }
    downloadFileFromServer() {
        console.log( this.courier, this.awbStatus );
        if (this.courier == null || this.awbStatus == null) {
            alert( 'Please Select Filters First' );
        } else {
            this.awbService.downloadFile(this.courier, this.awbStatus);
        }
        return ;
    }
}
