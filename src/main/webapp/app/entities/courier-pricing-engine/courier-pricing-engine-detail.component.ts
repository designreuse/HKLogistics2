import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';

@Component({
    selector: 'jhi-courier-pricing-engine-detail',
    templateUrl: './courier-pricing-engine-detail.component.html'
})
export class CourierPricingEngineDetailComponent implements OnInit {
    courierPricingEngine: ICourierPricingEngine;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courierPricingEngine }) => {
            this.courierPricingEngine = courierPricingEngine;
        });
    }

    previousState() {
        window.history.back();
    }
}
