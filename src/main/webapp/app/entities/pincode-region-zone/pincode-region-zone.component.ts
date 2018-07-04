import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';
import { Principal } from 'app/core';
import { PincodeRegionZoneService } from './pincode-region-zone.service';
import { ICourierGroup } from 'app/shared/model/courier-group.model';
import { CourierGroupService } from 'app/entities/courier-group';
import { IRegionType } from 'app/shared/model/region-type.model';
import { RegionTypeService } from 'app/entities/region-type';

@Component({
    selector: 'jhi-pincode-region-zone',
    templateUrl: './pincode-region-zone.component.html'
})
export class PincodeRegionZoneComponent implements OnInit, OnDestroy {
    pincodeRegionZones: IPincodeRegionZone[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    courierGroup: ICourierGroup;
    couriergroups: ICourierGroup[];
    sourcePincode: string;
    destinationPincode: string;
    regiontypes: IRegionType[];
    regionTypeId : number;

    constructor(
        private pincodeRegionZoneService: PincodeRegionZoneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal,
        private courierGroupService: CourierGroupService,
        private regionTypeService: RegionTypeService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['pincode']
                ? this.activatedRoute.snapshot.params['pincode']
                : '';
    }

    loadAll() {
        if (this.sourcePincode || this.destinationPincode || this.courierGroup ||
           this.regionTypeId) {
            this.pincodeRegionZoneService
                .filter(this.sourcePincode, this.destinationPincode,  this.courierGroup,
                     this.regionTypeId)
                .subscribe(
                    (res: HttpResponse<IPincodeRegionZone[]>) => (this.pincodeRegionZones = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.pincodeRegionZoneService.query().subscribe(
            (res: HttpResponse<IPincodeRegionZone[]>) => {
                this.pincodeRegionZones = res.body;
                this.currentSearch = '';
                this.courierGroup = null;
                this.sourcePincode = '';
                this.destinationPincode = '';
                this.regionTypeId = null;
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
        this.courierGroupService.query().subscribe(
            (res: HttpResponse<ICourierGroup[]>) => {
                this.couriergroups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.regionTypeService.query().subscribe(
            (res: HttpResponse<IRegionType[]>) => {
                this.regiontypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPincodeRegionZones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPincodeRegionZone) {
        return item.id;
    }

    registerChangeInPincodeRegionZones() {
        this.eventSubscriber = this.eventManager.subscribe('pincodeRegionZoneListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCourierGroupById(index: number, item: ICourierGroup) {
        return item.id;
    }
    
    trackRegionTypeById(index: number, item: IRegionType) {
        return item.id;
    }

    filter() {
        console.log(this.courierGroup);
        if ( !this.destinationPincode) {
                alert("Please give Destination Pincode");
        } else {
                 this.pincodeRegionZoneService.filter(this.sourcePincode, this.destinationPincode, this.courierGroup,
                     this.regionTypeId).subscribe(
                    (res: HttpResponse<IPincodeRegionZone[]>) => (this.pincodeRegionZones = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
        return;
    }

    clearFilter() {
        this.courierGroup = null;
        this.sourcePincode = '';
        this.destinationPincode = '';
        this.regionTypeId = null;
        this.loadAll();
    }

    upload(event) {
        const elem = event.target;
        if (elem.files.length > 0) {
          const fileSelected: File = elem.files[0];
          if (fileSelected.name.substring(fileSelected.name.lastIndexOf('.')) !== '.xls') {
            return this.jhiAlertService.error('Please upload .xls file!', null, null);
          }
          this.pincodeRegionZoneService.uploadFile(fileSelected)
             .subscribe( response => {
          console.log('set any success actions...');
          this.jhiAlertService.success('uploaded file please refresh after sometime', null, null);
         // this.loadAll();
          return response;
    }.
     error => {
       console.log(error.message);
       this.jhiAlertService.error(error.statusText, null, null);
     });
        event.target.value = null;
        }
    }
}
