import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';
import { Principal } from 'app/core';
import { SourceDestinationMappingService } from './source-destination-mapping.service';

@Component({
    selector: 'jhi-source-destination-mapping',
    templateUrl: './source-destination-mapping.component.html'
})
export class SourceDestinationMappingComponent implements OnInit, OnDestroy {
    sourceDestinationMappings: ISourceDestinationMapping[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private sourceDestinationMappingService: SourceDestinationMappingService,
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
            this.sourceDestinationMappingService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ISourceDestinationMapping[]>) => (this.sourceDestinationMappings = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.sourceDestinationMappingService.query().subscribe(
            (res: HttpResponse<ISourceDestinationMapping[]>) => {
                this.sourceDestinationMappings = res.body;
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
        this.registerChangeInSourceDestinationMappings();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISourceDestinationMapping) {
        return item.id;
    }

    registerChangeInSourceDestinationMappings() {
        this.eventSubscriber = this.eventManager.subscribe('sourceDestinationMappingListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
