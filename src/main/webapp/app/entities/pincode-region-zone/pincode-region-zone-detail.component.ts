import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';

@Component({
    selector: 'jhi-pincode-region-zone-detail',
    templateUrl: './pincode-region-zone-detail.component.html'
})
export class PincodeRegionZoneDetailComponent implements OnInit {
    pincodeRegionZone: IPincodeRegionZone;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pincodeRegionZone }) => {
            this.pincodeRegionZone = pincodeRegionZone;
        });
    }

    previousState() {
        window.history.back();
    }
}
