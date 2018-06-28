import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';
import { VendorWHCourierMappingService } from './vendor-wh-courier-mapping.service';

@Component({
    selector: 'jhi-vendor-wh-courier-mapping-delete-dialog',
    templateUrl: './vendor-wh-courier-mapping-delete-dialog.component.html'
})
export class VendorWHCourierMappingDeleteDialogComponent {
    vendorWHCourierMapping: IVendorWHCourierMapping;

    constructor(
        private vendorWHCourierMappingService: VendorWHCourierMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vendorWHCourierMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vendorWHCourierMappingListModification',
                content: 'Deleted an vendorWHCourierMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vendor-wh-courier-mapping-delete-popup',
    template: ''
})
export class VendorWHCourierMappingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vendorWHCourierMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VendorWHCourierMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.vendorWHCourierMapping = vendorWHCourierMapping;
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
