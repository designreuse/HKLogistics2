/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HkLogisticsTestModule } from '../../../test.module';
import { ProductVariantDeleteDialogComponent } from 'app/entities/product-variant/product-variant-delete-dialog.component';
import { ProductVariantService } from 'app/entities/product-variant/product-variant.service';

describe('Component Tests', () => {
    describe('ProductVariant Management Delete Component', () => {
        let comp: ProductVariantDeleteDialogComponent;
        let fixture: ComponentFixture<ProductVariantDeleteDialogComponent>;
        let service: ProductVariantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ProductVariantDeleteDialogComponent]
            })
                .overrideTemplate(ProductVariantDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductVariantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductVariantService);
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
