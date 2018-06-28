import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChannel } from 'app/shared/model/channel.model';

@Component({
    selector: 'jhi-channel-detail',
    templateUrl: './channel-detail.component.html'
})
export class ChannelDetailComponent implements OnInit {
    channel: IChannel;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ channel }) => {
            this.channel = channel;
        });
    }

    previousState() {
        window.history.back();
    }
}
