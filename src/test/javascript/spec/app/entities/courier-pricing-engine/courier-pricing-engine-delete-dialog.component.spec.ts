/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierPricingEngineDeleteDialogComponent } from 'app/entities/courier-pricing-engine/courier-pricing-engine-delete-dialog.component';
import { CourierPricingEngineService } from 'app/entities/courier-pricing-engine/courier-pricing-engine.service';

describe('Component Tests', () => {
    describe('CourierPricingEngine Management Delete Component', () => {
        let comp: CourierPricingEngineDeleteDialogComponent;
        let fixture: ComponentFixture<CourierPricingEngineDeleteDialogComponent>;
        let service: CourierPricingEngineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierPricingEngineDeleteDialogComponent]
            })
                .overrideTemplate(CourierPricingEngineDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourierPricingEngineDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierPricingEngineService);
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
