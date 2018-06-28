import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAwb } from 'app/shared/model/awb.model';

@Component({
    selector: 'jhi-awb-detail',
    templateUrl: './awb-detail.component.html'
})
export class AwbDetailComponent implements OnInit {
    awb: IAwb;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ awb }) => {
            this.awb = awb;
        });
    }

    previousState() {
        window.history.back();
    }
}
