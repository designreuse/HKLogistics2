import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourierChannel } from 'app/shared/model/courier-channel.model';

@Component({
    selector: 'jhi-courier-channel-detail',
    templateUrl: './courier-channel-detail.component.html'
})
export class CourierChannelDetailComponent implements OnInit {
    courierChannel: ICourierChannel;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courierChannel }) => {
            this.courierChannel = courierChannel;
        });
    }

    previousState() {
        window.history.back();
    }
}
