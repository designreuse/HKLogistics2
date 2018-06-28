import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IState } from 'app/shared/model/state.model';

@Component({
    selector: 'jhi-state-detail',
    templateUrl: './state-detail.component.html'
})
export class StateDetailComponent implements OnInit {
    state: IState;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ state }) => {
            this.state = state;
        });
    }

    previousState() {
        window.history.back();
    }
}
