import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHub } from 'app/shared/model/hub.model';

@Component({
    selector: 'jhi-hub-detail',
    templateUrl: './hub-detail.component.html'
})
export class HubDetailComponent implements OnInit {
    hub: IHub;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hub }) => {
            this.hub = hub;
        });
    }

    previousState() {
        window.history.back();
    }
}
