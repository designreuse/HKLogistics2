import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegionType } from 'app/shared/model/region-type.model';

@Component({
    selector: 'jhi-region-type-detail',
    templateUrl: './region-type-detail.component.html'
})
export class RegionTypeDetailComponent implements OnInit {
    regionType: IRegionType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ regionType }) => {
            this.regionType = regionType;
        });
    }

    previousState() {
        window.history.back();
    }
}
