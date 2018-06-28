import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourierChannel } from 'app/shared/model/courier-channel.model';
import { CourierChannelService } from './courier-channel.service';

@Component({
    selector: 'jhi-courier-channel-delete-dialog',
    templateUrl: './courier-channel-delete-dialog.component.html'
})
export class CourierChannelDeleteDialogComponent {
    courierChannel: ICourierChannel;

    constructor(
        private courierChannelService: CourierChannelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courierChannelService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'courierChannelListModification',
                content: 'Deleted an courierChannel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-courier-channel-delete-popup',
    template: ''
})
export class CourierChannelDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courierChannel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CourierChannelDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.courierChannel = courierChannel;
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
