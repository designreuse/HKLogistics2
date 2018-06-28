import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourierChannel } from 'app/shared/model/courier-channel.model';
import { CourierChannelService } from './courier-channel.service';
import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from 'app/entities/channel';
import { ICourier } from 'app/shared/model/courier.model';
import { CourierService } from 'app/entities/courier';

@Component({
    selector: 'jhi-courier-channel-update',
    templateUrl: './courier-channel-update.component.html'
})
export class CourierChannelUpdateComponent implements OnInit {
    private _courierChannel: ICourierChannel;
    isSaving: boolean;

    channels: IChannel[];

    couriers: ICourier[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private courierChannelService: CourierChannelService,
        private channelService: ChannelService,
        private courierService: CourierService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courierChannel }) => {
            this.courierChannel = courierChannel;
        });
        this.channelService.query().subscribe(
            (res: HttpResponse<IChannel[]>) => {
                this.channels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.courierService.query().subscribe(
            (res: HttpResponse<ICourier[]>) => {
                this.couriers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courierChannel.id !== undefined) {
            this.subscribeToSaveResponse(this.courierChannelService.update(this.courierChannel));
        } else {
            this.subscribeToSaveResponse(this.courierChannelService.create(this.courierChannel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourierChannel>>) {
        result.subscribe((res: HttpResponse<ICourierChannel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackChannelById(index: number, item: IChannel) {
        return item.id;
    }

    trackCourierById(index: number, item: ICourier) {
        return item.id;
    }
    get courierChannel() {
        return this._courierChannel;
    }

    set courierChannel(courierChannel: ICourierChannel) {
        this._courierChannel = courierChannel;
    }
}
