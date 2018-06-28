/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { ShipmentServiceTypeUpdateComponent } from 'app/entities/shipment-service-type/shipment-service-type-update.component';
import { ShipmentServiceTypeService } from 'app/entities/shipment-service-type/shipment-service-type.service';
import { ShipmentServiceType } from 'app/shared/model/shipment-service-type.model';

describe('Component Tests', () => {
    describe('ShipmentServiceType Management Update Component', () => {
        let comp: ShipmentServiceTypeUpdateComponent;
        let fixture: ComponentFixture<ShipmentServiceTypeUpdateComponent>;
        let service: ShipmentServiceTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ShipmentServiceTypeUpdateComponent]
            })
                .overrideTemplate(ShipmentServiceTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShipmentServiceTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipmentServiceTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ShipmentServiceType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.shipmentServiceType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ShipmentServiceType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.shipmentServiceType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
