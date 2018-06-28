/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { ShipmentServiceTypeDetailComponent } from 'app/entities/shipment-service-type/shipment-service-type-detail.component';
import { ShipmentServiceType } from 'app/shared/model/shipment-service-type.model';

describe('Component Tests', () => {
    describe('ShipmentServiceType Management Detail Component', () => {
        let comp: ShipmentServiceTypeDetailComponent;
        let fixture: ComponentFixture<ShipmentServiceTypeDetailComponent>;
        const route = ({ data: of({ shipmentServiceType: new ShipmentServiceType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [ShipmentServiceTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ShipmentServiceTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShipmentServiceTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.shipmentServiceType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
