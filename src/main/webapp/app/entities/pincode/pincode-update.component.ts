import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPincode } from 'app/shared/model/pincode.model';
import { PincodeService } from './pincode.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city';
import { IState } from 'app/shared/model/state.model';
import { StateService } from 'app/entities/state';
import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from 'app/entities/zone';
import { IHub } from 'app/shared/model/hub.model';
import { HubService } from 'app/entities/hub';

@Component({
    selector: 'jhi-pincode-update',
    templateUrl: './pincode-update.component.html'
})
export class PincodeUpdateComponent implements OnInit {
    private _pincode: IPincode;
    isSaving: boolean;

    cities: ICity[];

    states: IState[];

    zones: IZone[];

    hubs: IHub[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pincodeService: PincodeService,
        private cityService: CityService,
        private stateService: StateService,
        private zoneService: ZoneService,
        private hubService: HubService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pincode }) => {
            this.pincode = pincode;
        });
        this.cityService.query().subscribe(
            (res: HttpResponse<ICity[]>) => {
                this.cities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stateService.query().subscribe(
            (res: HttpResponse<IState[]>) => {
                this.states = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.zoneService.query().subscribe(
            (res: HttpResponse<IZone[]>) => {
                this.zones = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.hubService.query().subscribe(
            (res: HttpResponse<IHub[]>) => {
                this.hubs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pincode.id !== undefined) {
            this.subscribeToSaveResponse(this.pincodeService.update(this.pincode));
        } else {
            this.subscribeToSaveResponse(this.pincodeService.create(this.pincode));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPincode>>) {
        result.subscribe((res: HttpResponse<IPincode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCityById(index: number, item: ICity) {
        return item.id;
    }

    trackStateById(index: number, item: IState) {
        return item.id;
    }

    trackZoneById(index: number, item: IZone) {
        return item.id;
    }

    trackHubById(index: number, item: IHub) {
        return item.id;
    }
    get pincode() {
        return this._pincode;
    }

    set pincode(pincode: IPincode) {
        this._pincode = pincode;
    }
}
