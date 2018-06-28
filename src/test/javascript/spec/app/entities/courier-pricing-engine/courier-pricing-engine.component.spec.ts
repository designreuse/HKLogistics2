/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierPricingEngineComponent } from 'app/entities/courier-pricing-engine/courier-pricing-engine.component';
import { CourierPricingEngineService } from 'app/entities/courier-pricing-engine/courier-pricing-engine.service';
import { CourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';

describe('Component Tests', () => {
    describe('CourierPricingEngine Management Component', () => {
        let comp: CourierPricingEngineComponent;
        let fixture: ComponentFixture<CourierPricingEngineComponent>;
        let service: CourierPricingEngineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierPricingEngineComponent],
                providers: []
            })
                .overrideTemplate(CourierPricingEngineComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourierPricingEngineComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierPricingEngineService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CourierPricingEngine(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.courierPricingEngines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
