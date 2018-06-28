import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';
import { SourceDestinationMappingService } from './source-destination-mapping.service';

@Component({
    selector: 'jhi-source-destination-mapping-update',
    templateUrl: './source-destination-mapping-update.component.html'
})
export class SourceDestinationMappingUpdateComponent implements OnInit {
    private _sourceDestinationMapping: ISourceDestinationMapping;
    isSaving: boolean;

    constructor(private sourceDestinationMappingService: SourceDestinationMappingService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sourceDestinationMapping }) => {
            this.sourceDestinationMapping = sourceDestinationMapping;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sourceDestinationMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.sourceDestinationMappingService.update(this.sourceDestinationMapping));
        } else {
            this.subscribeToSaveResponse(this.sourceDestinationMappingService.create(this.sourceDestinationMapping));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISourceDestinationMapping>>) {
        result.subscribe(
            (res: HttpResponse<ISourceDestinationMapping>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get sourceDestinationMapping() {
        return this._sourceDestinationMapping;
    }

    set sourceDestinationMapping(sourceDestinationMapping: ISourceDestinationMapping) {
        this._sourceDestinationMapping = sourceDestinationMapping;
    }
}
