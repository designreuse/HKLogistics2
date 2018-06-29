import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';
import { PincodeCourierMappingService } from './pincode-courier-mapping.service';
import { IVendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';
import { VendorWHCourierMappingService } from 'app/entities/vendor-wh-courier-mapping';
import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';
import { SourceDestinationMappingService } from 'app/entities/source-destination-mapping';

@Component({
    selector: 'jhi-pincode-courier-mapping-update',
    templateUrl: './pincode-courier-mapping-update.component.html'
})
export class PincodeCourierMappingUpdateComponent implements OnInit {
    private _pincodeCourierMapping: IPincodeCourierMapping;
    isSaving: boolean;

    vendorwhcouriermappings: IVendorWHCourierMapping[];

    sourcedestinationmappings: ISourceDestinationMapping[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pincodeCourierMappingService: PincodeCourierMappingService,
        private vendorWHCourierMappingService: VendorWHCourierMappingService,
        private sourceDestinationMappingService: SourceDestinationMappingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pincodeCourierMapping }) => {
            this.pincodeCourierMapping = pincodeCourierMapping;
        });
        this.vendorWHCourierMappingService.query().subscribe(
            (res: HttpResponse<IVendorWHCourierMapping[]>) => {
                this.vendorwhcouriermappings = res.body;
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
        if (this.pincodeCourierMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.pincodeCourierMappingService.update(this.pincodeCourierMapping));
        } else {
            this.subscribeToSaveResponse(this.pincodeCourierMappingService.create(this.pincodeCourierMapping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPincodeCourierMapping>>) {
        result.subscribe(
            (res: HttpResponse<IPincodeCourierMapping>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackVendorWHCourierMappingById(index: number, item: IVendorWHCourierMapping) {
        return item.id;
    }

    trackSourceDestinationMappingById(index: number, item: ISourceDestinationMapping) {
        return item.id;
    }
    get pincodeCourierMapping() {
        return this._pincodeCourierMapping;
    }

    set pincodeCourierMapping(pincodeCourierMapping: IPincodeCourierMapping) {
        this._pincodeCourierMapping = pincodeCourierMapping;
    }
}
