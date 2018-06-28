import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAwbStatus } from 'app/shared/model/awb-status.model';

@Component({
    selector: 'jhi-awb-status-detail',
    templateUrl: './awb-status-detail.component.html'
})
export class AwbStatusDetailComponent implements OnInit {
    awbStatus: IAwbStatus;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ awbStatus }) => {
            this.awbStatus = awbStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
