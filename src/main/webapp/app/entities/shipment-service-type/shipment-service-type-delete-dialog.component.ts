import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShipmentServiceType } from 'app/shared/model/shipment-service-type.model';
import { ShipmentServiceTypeService } from './shipment-service-type.service';

@Component({
    selector: 'jhi-shipment-service-type-delete-dialog',
    templateUrl: './shipment-service-type-delete-dialog.component.html'
})
export class ShipmentServiceTypeDeleteDialogComponent {
    shipmentServiceType: IShipmentServiceType;

    constructor(
        private shipmentServiceTypeService: ShipmentServiceTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shipmentServiceTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'shipmentServiceTypeListModification',
                content: 'Deleted an shipmentServiceType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shipment-service-type-delete-popup',
    template: ''
})
export class ShipmentServiceTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shipmentServiceType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ShipmentServiceTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.shipmentServiceType = shipmentServiceType;
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
