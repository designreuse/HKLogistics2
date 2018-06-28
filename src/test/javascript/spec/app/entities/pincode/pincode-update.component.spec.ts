/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HkLogisticsTestModule } from '../../../test.module';
import { PincodeUpdateComponent } from 'app/entities/pincode/pincode-update.component';
import { PincodeService } from 'app/entities/pincode/pincode.service';
import { Pincode } from 'app/shared/model/pincode.model';

describe('Component Tests', () => {
    describe('Pincode Management Update Component', () => {
        let comp: PincodeUpdateComponent;
        let fixture: ComponentFixture<PincodeUpdateComponent>;
        let service: PincodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HkLogisticsTestModule],
                declarations: [PincodeUpdateComponent]
            })
                .overrideTemplate(PincodeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PincodeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PincodeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pincode(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pincode = entity;
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
                    const entity = new Pincode();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pincode = entity;
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
