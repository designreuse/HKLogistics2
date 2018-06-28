import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourierGroup } from 'app/shared/model/courier-group.model';
import { CourierGroupService } from './courier-group.service';

@Component({
    selector: 'jhi-courier-group-delete-dialog',
    templateUrl: './courier-group-delete-dialog.component.html'
})
export class CourierGroupDeleteDialogComponent {
    courierGroup: ICourierGroup;

    constructor(
        private courierGroupService: CourierGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courierGroupService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'courierGroupListModification',
                content: 'Deleted an courierGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-courier-group-delete-popup',
    template: ''
})
export class CourierGroupDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courierGroup }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CourierGroupDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.courierGroup = courierGroup;
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
