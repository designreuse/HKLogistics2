import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IHub } from 'app/shared/model/hub.model';
import { HubService } from './hub.service';

@Component({
    selector: 'jhi-hub-update',
    templateUrl: './hub-update.component.html'
})
export class HubUpdateComponent implements OnInit {
    private _hub: IHub;
    isSaving: boolean;

    constructor(private hubService: HubService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hub }) => {
            this.hub = hub;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hub.id !== undefined) {
            this.subscribeToSaveResponse(this.hubService.update(this.hub));
        } else {
            this.subscribeToSaveResponse(this.hubService.create(this.hub));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHub>>) {
        result.subscribe((res: HttpResponse<IHub>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get hub() {
        return this._hub;
    }

    set hub(hub: IHub) {
        this._hub = hub;
    }
}
