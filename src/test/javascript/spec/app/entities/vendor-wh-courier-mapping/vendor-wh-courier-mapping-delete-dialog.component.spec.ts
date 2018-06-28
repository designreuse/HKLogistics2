/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HkLogisticsTestModule } from '../../../test.module';
import { VendorWHCourierMappingDeleteDialogComponent } from 'app/entities/vendor-wh-courier-mapping/vendor-wh-courier-mapping-delete-dialog.component';
import { VendorWHCourierMappingService } from 'app/entities/vendor-wh-courier-mapping/vendor-wh-courier-mapping.service';

describe('Component Tests', () => {
    describe('VendorWHCourierMapping Management Delete Component', () => {
        let comp: VendorWHCourierMappingDeleteDialogComponent;
        let fixture: ComponentFixture<VendorWHCourierMappingDeleteDialogComponent>;
        let service: VendorWHCourierMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [VendorWHCourierMappingDeleteDialogComponent]
            })
                .overrideTemplate(VendorWHCourierMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VendorWHCourierMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendorWHCourierMappingService);
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
