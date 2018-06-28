import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';
import { PincodeRegionZoneService } from './pincode-region-zone.service';
import { IRegionType } from 'app/shared/model/region-type.model';
import { RegionTypeService } from 'app/entities/region-type';
import { ICourierGroup } from 'app/shared/model/courier-group.model';
import { CourierGroupService } from 'app/entities/courier-group';
import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';
import { SourceDestinationMappingService } from 'app/entities/source-destination-mapping';

@Component({
    selector: 'jhi-pincode-region-zone-update',
    templateUrl: './pincode-region-zone-update.component.html'
})
export class PincodeRegionZoneUpdateComponent implements OnInit {
    private _pincodeRegionZone: IPincodeRegionZone;
    isSaving: boolean;

    regiontypes: IRegionType[];

    couriergroups: ICourierGroup[];

    sourcedestinationmappings: ISourceDestinationMapping[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pincodeRegionZoneService: PincodeRegionZoneService,
        private regionTypeService: RegionTypeService,
        private courierGroupService: CourierGroupService,
        private sourceDestinationMappingService: SourceDestinationMappingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pincodeRegionZone }) => {
            this.pincodeRegionZone = pincodeRegionZone;
        });
        this.regionTypeService.query().subscribe(
            (res: HttpResponse<IRegionType[]>) => {
                this.regiontypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.courierGroupService.query().subscribe(
            (res: HttpResponse<ICourierGroup[]>) => {
                this.couriergroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sourceDestinationMappingService.query().subscribe(
            (res: HttpResponse<ISourceDestinationMapping[]>) => {
                this.sourcedestinationmappings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pincodeRegionZone.id !== undefined) {
            this.subscribeToSaveResponse(this.pincodeRegionZoneService.update(this.pincodeRegionZone));
        } else {
            this.subscribeToSaveResponse(this.pincodeRegionZoneService.create(this.pincodeRegionZone));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPincodeRegionZone>>) {
        result.subscribe((res: HttpResponse<IPincodeRegionZone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRegionTypeById(index: number, item: IRegionType) {
        return item.id;
    }

    trackCourierGroupById(index: number, item: ICourierGroup) {
        return item.id;
    }

    trackSourceDestinationMappingById(index: number, item: ISourceDestinationMapping) {
        return item.id;
    }
    get pincodeRegionZone() {
        return this._pincodeRegionZone;
    }

    set pincodeRegionZone(pincodeRegionZone: IPincodeRegionZone) {
        this._pincodeRegionZone = pincodeRegionZone;
    }
}
