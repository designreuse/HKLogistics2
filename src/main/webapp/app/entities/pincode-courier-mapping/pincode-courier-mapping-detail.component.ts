import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';

@Component({
    selector: 'jhi-pincode-courier-mapping-detail',
    templateUrl: './pincode-courier-mapping-detail.component.html'
})
export class PincodeCourierMappingDetailComponent implements OnInit {
    pincodeCourierMapping: IPincodeCourierMapping;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pincodeCourierMapping }) => {
            this.pincodeCourierMapping = pincodeCourierMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
