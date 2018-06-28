import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAwbStatus } from 'app/shared/model/awb-status.model';
import { AwbStatusService } from './awb-status.service';

@Component({
    selector: 'jhi-awb-status-delete-dialog',
    templateUrl: './awb-status-delete-dialog.component.html'
})
export class AwbStatusDeleteDialogComponent {
    awbStatus: IAwbStatus;

    constructor(private awbStatusService: AwbStatusService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.awbStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'awbStatusListModification',
                content: 'Deleted an awbStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-awb-status-delete-popup',
    template: ''
})
export class AwbStatusDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ awbStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AwbStatusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.awbStatus = awbStatus;
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
