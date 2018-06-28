import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPincode } from 'app/shared/model/pincode.model';

@Component({
    selector: 'jhi-pincode-detail',
    templateUrl: './pincode-detail.component.html'
})
export class PincodeDetailComponent implements OnInit {
    pincode: IPincode;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pincode }) => {
            this.pincode = pincode;
        });
    }

    previousState() {
        window.history.back();
    }
}
