import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICourier } from 'app/shared/model/courier.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CourierService } from './courier.service';
import { ICourierGroup } from 'app/shared/model/courier-group.model';
import { CourierGroupService } from 'app/entities/courier-group';

@Component({
    selector: 'jhi-courier',
    templateUrl: './courier.component.html'
})
export class CourierComponent implements OnInit, OnDestroy {
    currentAccount: any;
    couriers: ICourier[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearchName: string;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    couriergroups: ICourierGroup[];
    courierGroup: ICourierGroup;
    status: String;
    operation: String;

    constructor(
        private courierService: CourierService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private courierGroupService: CourierGroupService,
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearchName =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['searchName']
                ? this.activatedRoute.snapshot.params['searchName']
                : '';
    }

    loadAll() {
        if (this.currentSearchName || this.courierGroup) {
            this.courierService
                .filter(this.courierGroup, this.currentSearchName, this.status , this.operation {
                    page: this.page - 1,
                    // query: this.currentSearchName,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICourier[]>) => this.paginateCouriers(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.courierService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ICourier[]>) => this.paginateCouriers(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/courier'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                searchName: this.currentSearchName,
                courierGroup: this.courierGroup,
                status: this.status,
                operation: this.operation,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clearName() {
        this.page = 0;
        this.currentSearchName = '';
        this.router.navigate([
            '/courier',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    clearFilter() {
        this.page = 0;
        this.currentSearchName = '';
        this.courierGroup = null;
        this.status = '';
        this.operation = '';
        this.router.navigate([
            '/courier',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    searchName(query) {
        if (!query) {
            return this.clearName();
        }
        this.page = 0;
        this.currentSearchName = query;
        this.router.navigate([
            '/courier',
            {
                searchName: this.currentSearchName,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.courierGroupService.query().subscribe(
            (res: HttpResponse<ICourierGroup[]>) => {
                this.couriergroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCouriers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICourier) {
        return item.id;
    }

    registerChangeInCouriers() {
        this.eventSubscriber = this.eventManager.subscribe('courierListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateCouriers(data: ICourier[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        console.log('data ->>>>', data)
        this.couriers = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCourierGroupById(index: number, item: ICourierGroup) {
        return item.id;
    }

    filter() {
        console.log(this.courierGroup);
        console.log(this.currentSearchName);
        console.log(this.status);
        console.log(this.operation);
        let queryParamsFilters = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }
        if ( !this.courierGroup && !this.currentSearchName && !this.status && !this.operation) {
                alert("Please Select Filters First");
        } else {
                 this.courierService.filter(this.courierGroup, this.currentSearchName, this.status, this.operation, queryParamsFilters).subscribe(
                    (res: HttpResponse<ICourier[]>) => this.paginateCouriers(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
        return;
    }
}
