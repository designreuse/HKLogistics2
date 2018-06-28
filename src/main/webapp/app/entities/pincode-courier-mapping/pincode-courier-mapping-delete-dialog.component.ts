import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';
import { PincodeCourierMappingService } from './pincode-courier-mapping.service';

@Component({
    selector: 'jhi-pincode-courier-mapping-delete-dialog',
    templateUrl: './pincode-courier-mapping-delete-dialog.component.html'
})
export class PincodeCourierMappingDeleteDialogComponent {
    pincodeCourierMapping: IPincodeCourierMapping;

    constructor(
        private pincodeCourierMappingService: PincodeCourierMappingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pincodeCourierMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pincodeCourierMappingListModification',
                content: 'Deleted an pincodeCourierMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pincode-courier-mapping-delete-popup',
    template: ''
})
export class PincodeCourierMappingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pincodeCourierMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PincodeCourierMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pincodeCourierMapping = pincodeCourierMapping;
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
