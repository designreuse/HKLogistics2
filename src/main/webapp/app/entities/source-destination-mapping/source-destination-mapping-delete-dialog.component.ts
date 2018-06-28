import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISourceDestinationMapping } from 'app/shared/model/source-destination-mapping.model';
import { SourceDestinationMappingService } from './source-destination-mapping.service';

@Component({
    selector: 'jhi-source-destination-mapping-delete-dialog',
    templateUrl: './source-destination-mapping-delete-dialog.component.html'
})
export class SourceDestinationMappingDeleteDialogComponent {
    sourceDestinationMapping: ISourceDestinationMapping;

    constructor(
        private sourceDestinationMappingService: SourceDestinationMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceDestinationMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sourceDestinationMappingListModification',
                content: 'Deleted an sourceDestinationMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-destination-mapping-delete-popup',
    template: ''
})
export class SourceDestinationMappingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sourceDestinationMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SourceDestinationMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sourceDestinationMapping = sourceDestinationMapping;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
