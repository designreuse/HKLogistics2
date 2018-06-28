import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';

@Component({
    selector: 'jhi-vendor-wh-courier-mapping-detail',
    templateUrl: './vendor-wh-courier-mapping-detail.component.html'
})
export class VendorWHCourierMappingDetailComponent implements OnInit {
    vendorWHCourierMapping: IVendorWHCourierMapping;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vendorWHCourierMapping }) => {
            this.vendorWHCourierMapping = vendorWHCourierMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
