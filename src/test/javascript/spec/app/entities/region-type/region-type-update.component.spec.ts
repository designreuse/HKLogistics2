/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { RegionTypeUpdateComponent } from 'app/entities/region-type/region-type-update.component';
import { RegionTypeService } from 'app/entities/region-type/region-type.service';
import { RegionType } from 'app/shared/model/region-type.model';

describe('Component Tests', () => {
    describe('RegionType Management Update Component', () => {
        let comp: RegionTypeUpdateComponent;
        let fixture: ComponentFixture<RegionTypeUpdateComponent>;
        let service: RegionTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [RegionTypeUpdateComponent]
            })
                .overrideTemplate(RegionTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegionTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegionTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RegionType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regionType = entity;
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
                    const entity = new RegionType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.regionType = entity;
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
