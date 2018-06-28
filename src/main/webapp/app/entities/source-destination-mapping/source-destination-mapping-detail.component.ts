import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';

@Component({
    selector: 'jhi-source-destination-mapping-detail',
    templateUrl: './source-destination-mapping-detail.component.html'
})
export class SourceDestinationMappingDetailComponent implements OnInit {
    sourceDestinationMapping: ISourceDestinationMapping;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceDestinationMapping }) => {
            this.sourceDestinationMapping = sourceDestinationMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}
