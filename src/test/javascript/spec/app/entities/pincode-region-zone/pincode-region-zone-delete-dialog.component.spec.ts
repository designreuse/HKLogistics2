/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeRegionZoneDeleteDialogComponent } from 'app/entities/pincode-region-zone/pincode-region-zone-delete-dialog.component';
import { PincodeRegionZoneService } from 'app/entities/pincode-region-zone/pincode-region-zone.service';

describe('Component Tests', () => {
    describe('PincodeRegionZone Management Delete Component', () => {
        let comp: PincodeRegionZoneDeleteDialogComponent;
        let fixture: ComponentFixture<PincodeRegionZoneDeleteDialogComponent>;
        let service: PincodeRegionZoneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeRegionZoneDeleteDialogComponent]
            })
                .overrideTemplate(PincodeRegionZoneDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PincodeRegionZoneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PincodeRegionZoneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
