import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from './zone.service';

@Component({
    selector: 'jhi-zone-update',
    templateUrl: './zone-update.component.html'
})
export class ZoneUpdateComponent implements OnInit {
    private _zone: IZone;
    isSaving: boolean;

    constructor(private zoneService: ZoneService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ zone }) => {
            this.zone = zone;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.zone.id !== undefined) {
            this.subscribeToSaveResponse(this.zoneService.update(this.zone));
        } else {
            this.subscribeToSaveResponse(this.zoneService.create(this.zone));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IZone>>) {
        result.subscribe((res: HttpResponse<IZone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get zone() {
        return this._zone;
    }

    set zone(zone: IZone) {
        this._zone = zone;
    }
}
