import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';
import { CourierPricingEngineService } from './courier-pricing-engine.service';

@Component({
    selector: 'jhi-courier-pricing-engine-delete-dialog',
    templateUrl: './courier-pricing-engine-delete-dialog.component.html'
})
export class CourierPricingEngineDeleteDialogComponent {
    courierPricingEngine: ICourierPricingEngine;

    constructor(
        private courierPricingEngineService: CourierPricingEngineService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courierPricingEngineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'courierPricingEngineListModification',
                content: 'Deleted an courierPricingEngine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-courier-pricing-engine-delete-popup',
    template: ''
})
export class CourierPricingEngineDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courierPricingEngine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CourierPricingEngineDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.courierPricingEngine = courierPricingEngine;
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
