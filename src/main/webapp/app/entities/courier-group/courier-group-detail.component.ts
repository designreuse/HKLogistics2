import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourierGroup } from 'app/shared/model/courier-group.model';

@Component({
    selector: 'jhi-courier-group-detail',
    templateUrl: './courier-group-detail.component.html'
})
export class CourierGroupDetailComponent implements OnInit {
    courierGroup: ICourierGroup;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courierGroup }) => {
            this.courierGroup = courierGroup;
        });
    }

    previousState() {
        window.history.back();
    }
}
