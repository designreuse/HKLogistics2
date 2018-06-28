/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierPricingEngineDetailComponent } from 'app/entities/courier-pricing-engine/courier-pricing-engine-detail.component';
import { CourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';

describe('Component Tests', () => {
    describe('CourierPricingEngine Management Detail Component', () => {
        let comp: CourierPricingEngineDetailComponent;
        let fixture: ComponentFixture<CourierPricingEngineDetailComponent>;
        const route = ({ data: of({ courierPricingEngine: new CourierPricingEngine(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierPricingEngineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourierPricingEngineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourierPricingEngineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courierPricingEngine).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
