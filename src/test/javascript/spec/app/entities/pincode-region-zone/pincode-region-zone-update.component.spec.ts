/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeRegionZoneUpdateComponent } from 'app/entities/pincode-region-zone/pincode-region-zone-update.component';
import { PincodeRegionZoneService } from 'app/entities/pincode-region-zone/pincode-region-zone.service';
import { PincodeRegionZone } from 'app/shared/model/pincode-region-zone.model';

describe('Component Tests', () => {
    describe('PincodeRegionZone Management Update Component', () => {
        let comp: PincodeRegionZoneUpdateComponent;
        let fixture: ComponentFixture<PincodeRegionZoneUpdateComponent>;
        let service: PincodeRegionZoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeRegionZoneUpdateComponent]
            })
                .overrideTemplate(PincodeRegionZoneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PincodeRegionZoneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PincodeRegionZoneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PincodeRegionZone(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pincodeRegionZone = entity;
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
                    const entity = new PincodeRegionZone();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pincodeRegionZone = entity;
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
