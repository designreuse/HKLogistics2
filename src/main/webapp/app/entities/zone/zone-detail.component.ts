import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZone } from 'app/shared/model/zone.model';

@Component({
    selector: 'jhi-zone-detail',
    templateUrl: './zone-detail.component.html'
})
export class ZoneDetailComponent implements OnInit {
    zone: IZone;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ zone }) => {
            this.zone = zone;
        });
    }

    previousState() {
        window.history.back();
    }
}
