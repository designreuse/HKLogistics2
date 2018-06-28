/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { CourierPricingEngineUpdateComponent } from 'app/entities/courier-pricing-engine/courier-pricing-engine-update.component';
import { CourierPricingEngineService } from 'app/entities/courier-pricing-engine/courier-pricing-engine.service';
import { CourierPricingEngine } from 'app/shared/model/courier-pricing-engine.model';

describe('Component Tests', () => {
    describe('CourierPricingEngine Management Update Component', () => {
        let comp: CourierPricingEngineUpdateComponent;
        let fixture: ComponentFixture<CourierPricingEngineUpdateComponent>;
        let service: CourierPricingEngineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [CourierPricingEngineUpdateComponent]
            })
                .overrideTemplate(CourierPricingEngineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourierPricingEngineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourierPricingEngineService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CourierPricingEngine(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courierPricingEngine = entity;
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
                    const entity = new CourierPricingEngine();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courierPricingEngine = entity;
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
