/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { ShipmentServiceTypeComponent } from 'app/entities/shipment-service-type/shipment-service-type.component';
import { ShipmentServiceTypeService } from 'app/entities/shipment-service-type/shipment-service-type.service';
import { ShipmentServiceType } from 'app/shared/model/shipment-service-type.model';

describe('Component Tests', () => {
    describe('ShipmentServiceType Management Component', () => {
        let comp: ShipmentServiceTypeComponent;
        let fixture: ComponentFixture<ShipmentServiceTypeComponent>;
        let service: ShipmentServiceTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ShipmentServiceTypeComponent],
                providers: []
            })
                .overrideTemplate(ShipmentServiceTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShipmentServiceTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShipmentServiceTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ShipmentServiceType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.shipmentServiceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
