/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeCourierMappingUpdateComponent } from 'app/entities/pincode-courier-mapping/pincode-courier-mapping-update.component';
import { PincodeCourierMappingService } from 'app/entities/pincode-courier-mapping/pincode-courier-mapping.service';
import { PincodeCourierMapping } from 'app/shared/model/pincode-courier-mapping.model';

describe('Component Tests', () => {
    describe('PincodeCourierMapping Management Update Component', () => {
        let comp: PincodeCourierMappingUpdateComponent;
        let fixture: ComponentFixture<PincodeCourierMappingUpdateComponent>;
        let service: PincodeCourierMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeCourierMappingUpdateComponent]
            })
                .overrideTemplate(PincodeCourierMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PincodeCourierMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PincodeCourierMappingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PincodeCourierMapping(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pincodeCourierMapping = entity;
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
                    const entity = new PincodeCourierMapping();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pincodeCourierMapping = entity;
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
