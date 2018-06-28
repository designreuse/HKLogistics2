import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from './channel.service';

@Component({
    selector: 'jhi-channel-update',
    templateUrl: './channel-update.component.html'
})
export class ChannelUpdateComponent implements OnInit {
    private _channel: IChannel;
    isSaving: boolean;

    constructor(private channelService: ChannelService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ channel }) => {
            this.channel = channel;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.channel.id !== undefined) {
            this.subscribeToSaveResponse(this.channelService.update(this.channel));
        } else {
            this.subscribeToSaveResponse(this.channelService.create(this.channel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChannel>>) {
        result.subscribe((res: HttpResponse<IChannel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get channel() {
        return this._channel;
    }

    set channel(channel: IChannel) {
        this._channel = channel;
    }
}
