import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';
import { PincodeRegionZoneService } from './pincode-region-zone.service';

@Component({
    selector: 'jhi-pincode-region-zone-delete-dialog',
    templateUrl: './pincode-region-zone-delete-dialog.component.html'
})
export class PincodeRegionZoneDeleteDialogComponent {
    pincodeRegionZone: IPincodeRegionZone;

    constructor(
        private pincodeRegionZoneService: PincodeRegionZoneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pincodeRegionZoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pincodeRegionZoneListModification',
                content: 'Deleted an pincodeRegionZone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pincode-region-zone-delete-popup',
    template: ''
})
export class PincodeRegionZoneDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pincodeRegionZone }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PincodeRegionZoneDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pincodeRegionZone = pincodeRegionZone;
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
