/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { VendorWHCourierMappingUpdateComponent } from 'app/entities/vendor-wh-courier-mapping/vendor-wh-courier-mapping-update.component';
import { VendorWHCourierMappingService } from 'app/entities/vendor-wh-courier-mapping/vendor-wh-courier-mapping.service';
import { VendorWHCourierMapping } from 'app/shared/model/vendor-wh-courier-mapping.model';

describe('Component Tests', () => {
    describe('VendorWHCourierMapping Management Update Component', () => {
        let comp: VendorWHCourierMappingUpdateComponent;
        let fixture: ComponentFixture<VendorWHCourierMappingUpdateComponent>;
        let service: VendorWHCourierMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [VendorWHCourierMappingUpdateComponent]
            })
                .overrideTemplate(VendorWHCourierMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VendorWHCourierMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VendorWHCourierMappingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VendorWHCourierMapping(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vendorWHCourierMapping = entity;
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
                    const entity = new VendorWHCourierMapping();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vendorWHCourierMapping = entity;
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
