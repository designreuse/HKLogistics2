import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourier } from 'app/shared/model/courier.model';

@Component({
    selector: 'jhi-courier-detail',
    templateUrl: './courier-detail.component.html'
})
export class CourierDetailComponent implements OnInit {
    courier: ICourier;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courier }) => {
            this.courier = courier;
        });
    }

    previousState() {
        window.history.back();
    }
}
