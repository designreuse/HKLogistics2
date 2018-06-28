import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IState } from 'app/shared/model/state.model';
import { StateService } from './state.service';

@Component({
    selector: 'jhi-state-update',
    templateUrl: './state-update.component.html'
})
export class StateUpdateComponent implements OnInit {
    private _state: IState;
    isSaving: boolean;

    constructor(private stateService: StateService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ state }) => {
            this.state = state;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.state.id !== undefined) {
            this.subscribeToSaveResponse(this.stateService.update(this.state));
        } else {
            this.subscribeToSaveResponse(this.stateService.create(this.state));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IState>>) {
        result.subscribe((res: HttpResponse<IState>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get state() {
        return this._state;
    }

    set state(state: IState) {
        this._state = state;
    }
}
